# DriverLedger — Live Data Sources

How the app gets real-time flights and ferries. Architecture rule: **one small backend polls the sources; phones never poll.** The backend computes the alert moments and sends Firebase Cloud Messaging (FCM) pushes — FCM is free, phones sleep, battery is untouched.

## BC Ferries (the easy win — free)
- **Current Conditions**: BC Ferries publishes live per-route conditions — next sailings, deck space available (%), sailing waits, delays, cancellations — the same data behind bcferries.com's current-conditions pages. Backend polls every ~3–5 minutes for Tsawwassen and Horseshoe Bay routes.
- **Schedules**: published sailing schedules for planned times and the "make the boat" calculator.
- **Vessel position (optional layer)**: ferries broadcast AIS; vessel-tracking sources can confirm a docking a few minutes out for the arrival push.
- Cost: $0. Data volume is tiny.

## Flights — YVR (layered, grows with revenue)
- **Layer 1 (free, launch)**: published arrival schedules + bank detection. YVR's money waves are predictable — Asia banks late morning, Europe banks afternoon, plus evening domestic peaks. Detect the banks, fire the 40-minute alerts.
- **Layer 2 (free)**: OpenSky Network — live aircraft positions from community ADS-B receivers. Confirms a flight is actually inbound/on time; catches major delays. Free for reasonable use.
- **Layer 3 (paid, only when premium revenue covers it)**: a commercial flight-status API (e.g., FlightAware AeroAPI class) for exact status, gate, and delay data.
- **Cost shape**: the backend polls for ALL users at once (e.g., YVR arrivals every ~5 min ≈ a few hundred calls/day total). Cost scales with polling, not with user count — so even the paid layer stays cheap at scale.

## Freshness target
"Real time" for our purpose = 3–5 minute freshness. A 40-minute warning does not need per-second data.

## Alert pipeline
1. Backend detects an event (arrival bank ETA, ferry docking, cancellation).
2. Computes lead time (driver setting: 20/40/60 min for flights; ~15 min for ferries).
3. Sends FCM push only to drivers on shift or with a planned shift.

## Weather (free)
- **Open-Meteo** — free, no API key, hourly precipitation forecasts: powers Rain Money alerts (rain onset + timing for Metro Vancouver).
- Environment and Climate Change Canada open data as a cross-check.

## Backend hosting — free servers only (locked by Nishan, 2026-11-13)
The tracking backend (polling flights, ferries, weather; sending pushes) must run at **$0**:
- **Launch: GitHub Actions scheduled workflows** — cron polls every ~5 minutes inside this public repo (Actions is free for public repositories). Zero extra accounts, everything in one place.
- **Alternative: Cloudflare Workers free tier** — cron triggers, generous free limits, good for polling + alert logic.
- **Scale option: an always-free cloud VM (e.g., Oracle Cloud free tier) or Nishan's own hardware at home** (old PC / mini-PC running the poller 24/7, electricity only).
- Push delivery: **Firebase Cloud Messaging — free** at any realistic scale for this app.
