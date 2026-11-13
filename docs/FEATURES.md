# DriverLedger — Feature Specification

## Interface & theme (driver requirements — Nishan, 2026-11-13)
- **Glanceable home screen:** three huge numbers — live net $/hr, today's total, goal pace — readable in one second at a red light. One-thumb, zero typing for core actions. Positioning Score + next alert pinned at top.
- **Auto day/night theme (owner rule):** **white/light interface in daytime, black interface at night, switching automatically** by local sunset/sunrise (location-based — Vancouver sunset ranges ~4:15 PM winter to ~9:20 PM summer, so never a fixed clock time). Night theme is true-black (OLED battery saving + no eye glare on night shifts).
- Settings: Auto (default) / Always light / Always dark override. Theme switch must never flash mid-glance — apply instantly at switch moment, keep both themes' layouts identical so nothing moves.
- Performance floor for all of the above: 60 fps minimum (see ROADMAP performance requirement).

## Wave 1 — Record (the ledger)

### Earnings core
- Shift tracking with live **net $/hour** (after fuel estimate), not gross.
- **Multi-app dashboard**: Uber + Lyft + DoorDash/Skip totals combined into one picture.
- **Dead-km tracker**: unpaid vs paid kilometers, shown per shift and per week.
- Surge, quest, and bonus logging into earnings.
- Personal heatmap: the driver's own best zones and hours from their history.

### Tax pack (premium)
- **CRA-compliant mileage log**: odometer readings, business vs personal split.
- **HST tracker**: HST collected, estimated amount owing, quarterly remittance reminders.
- **Receipt capture**: photo → auto-filed expense category (fuel, wash, repairs, phone).
- **Accountant export**: one-tap PDF/CSV of the full tax year.
- **Set-aside meter**: per-shift estimate of what to park for income tax + HST.

### Backfill (how history enters the app)
- Import downloaded statements / trip history (CSV) from Uber, Lyft, DoorDash — earnings, trips, and on-trip km for the whole year in minutes.
- Manual monthly totals for anything files don't cover.
- Import Uber's official **annual tax summary** PDF when it is issued (Jan–Feb) to reconcile the year exactly.
- Fuel/expense history: receipt-by-receipt or bank statement (CSV) import for major items.
- From install day forward, the app records everything itself; backfill is a one-time onboarding step.

## Wave 2 — Protect

- **Pay-check watchdog**: verifies trips against BC's gig-worker minimum rate for engaged time; flags underpaid trips.
- **Fare dispute assistant**: detects missing tolls/surcharges, wrong distances, unpaid cancellation fees; drafts the support message with trip details.
- **Deactivation shield**: ratings, commendations, and trip records backed up outside the gig platforms.
- **Goal mode**: "I need $X by Friday" — live pacing against the driver's real $/hour.
- **Best-start advisor**: from the driver's own data — when and where to start.
- **Maintenance by kilometers**: oil, brakes, tires — warned before failure.
- **Renewal radar**: vehicle inspection, rideshare insurance, business licence, driver's licence expiry reminders.
- **Fatigue nudge**: quiet break reminders on long shifts.
- **Family view**: household sees today's earnings live (opt-in).
- **Punjabi + English UI**, voice-note expense logging.

## Wave 3 — Think (the smart layer)

### Positioning Score
- All signals (flights, ferries, events, weather, personal history) fused into one live score per zone: "Surrey Central: 8.4/10 right now."

### YVR module
- **Flight-bank radar**: live YVR arrivals grouped into demand waves.
- **Push alerts 40 minutes before** an arrival bank lands (lead time configurable: 20/40/60 min), with current staging-lot wait estimate. Alerts only during shifts / planned shifts.
- **Queue truth**: crowdsourced staging-lot wait times + estimate from flight volume.
- Delayed-flight awareness; international vs domestic pickup-point guidance.

### Ferry module (BC Ferries, live)
- Real-time conditions for **Tsawwassen** and **Horseshoe Bay**: next sailings, deck-space availability, sailing waits, delays, cancellations.
- **Push alerts before a ferry docks** (full sailings = 300+ people needing rides) and on cancellations (stranded crowds need drivers).
- **"Make the boat" calculator**: sailing time − check-in cutoff − live traffic = leave-by time.

### Notification behavior (driver requirement — Nishan, 2026-11-13)
- **Flight and ferry alerts are delivered DURING trips too** — while the driver has a passenger. The 40-minute lead exists precisely so the alert spans the current trip: it tells the driver where to go *after drop-off*. Do NOT suppress these alerts during trips.
- Delivery style: standard glanceable notifications — never full-screen takeovers, never audible interruptions over navigation audio. One line, readable at a glance: what's coming, when, and the suggested move.

### Flight alert tiers (driver requirement — Nishan, 2026-11-13)
1. **Bank warning (~40 min out)** — strategic: an arrival wave is coming; plan the next move. Sent to drivers on shift.
2. **Final approach (~5 min out)** — tactical, **geo-scoped to drivers near YVR** (~20 min away): "landing in ~5 minutes" so a nearby driver can note it and position.
3. **Landed** — touchdown detected (ADS-B): immediate notification for nearby drivers.
- Every flight alert includes: **flight number, origin, terminal class — International / Domestic / Transborder (US)** — and **estimated passengers**: aircraft seat capacity × typical load factor, **always labeled as an estimate** (exact souls-on-board is not public data; airlines do not publish it).
- Note (driver knowledge): YVR ride-hail pickup is **same-level** — drivers do NOT need pickup-zone guidance by class. The class still matters for **timing**: international passengers clear customs first, so their curb wave emerges later after touchdown (~30–45 min) than domestic (~10–15 min). Alert timing should account for this lag.
- **Curb-arrival model (defaults set by Nishan, 2026-11-13):** after touchdown, the pickup wave reaches the curb at **~30 min (International)** and **~20 min (Domestic)**. **Transborder (US)** arrivals pre-clear customs on departure, so they behave like domestic (~20 min). Treat these as the *peak* of a wave, not a single moment: first passengers emerge ~10 min earlier, a tail follows later.
- The model should refine itself over time: adjust for aircraft size (wide-bodies deplane slower), bank congestion (several internationals landing together slow customs), and eventually LEARN the true lag per flight from anonymized driver pickup data in the app.

### Driver Comfort Map — WASHROOMS FIRST (owner-flagged HIGH importance, 2026-11-13)
Finding an open washroom at night is a real, nightly problem for drivers — this is a headline feature, not a footnote.
- Map of washrooms with **open-now truth**: seeded from public place data (24h Tim Hortons / McDonald's / A&W, gas-station stores, malls, YVR, highway rest areas) and kept honest by **driver reports**: still open? locked? door code needed? clean?
- One-tap driver confirmations keep status live ("open 👍 / locked 🔒 / code required") — the same crowdsource engine as gas prices and airport queues.
- Filters: open at THIS hour (night-first after 10 PM), no purchase needed, safe lit parking, cleanliness rating.
- Also on the map: 24h coffee, safe rest spots for a fatigue break.
- **Construction pit stops (driver knowledge — Nishan, 2026-11-13):** construction sites across the Lower Mainland (hundreds at any time) have portable toilets that are frequently unlocked and publicly accessible. Drivers can **report them in the app** — pin, unlocked/locked status, cleanliness, photo optional.
  - These pins are **short-lived**: construction sites move and finish, so a pit-stop pin **auto-expires in ~14 days** unless another driver re-confirms it.
  - Guardrail: report only units accessible from public space (street/sidewalk side) — never inside fenced sites. The app marks pins "driver-reported, public access" and removes disputed ones.

### One-tap navigation (driver requirement — Nishan, 2026-11-13)
- Tapping ANY map pin — pit stop, washroom, gas/charging spot, ferry terminal, airport point — opens a detail card with live status and one big **Navigate** button.
- One tap on Navigate hands off to the driver's maps app (Google Maps or Waze, their choice in settings) with the route started. Free: uses the phone's existing navigation apps via intents — no maps API cost in our app, no typing an address while driving.
- **Rain Money**: rain onset alerts tied to the driver's own rainy-day earnings uplift.
- **Border Watch**: live Peace Arch / Pacific Crossing wait times.
- **Event radar**: Rogers Arena / BC Place events and start/end times as demand events.

### Energy radar
- **Cheap gas radar**: community-reported fuel prices; alert when near a cheaper station with a low tank (estimated $ saved shown).
- **EV charging**: Tesla Supercharger pricing has NO public API (prices live in Tesla's app/in-car). Approach: crowdsourced price reports from drivers (same pattern as gas), manual quick-entry, and a charging-cost calculator (price × kWh + idle-fee awareness).

### Idle-Killer
- After 20 unpaid minutes in one spot: suggests the best repositioning move with expected value ("YVR queue, 12 min away, ≈ +$18 expected").

### Tomorrow Briefing
- One evening push: tomorrow's start time, YVR banks, ferry dockings, events, weather, and an earnings estimate for the planned shift length.

### Punjabi Voice Co-Pilot
- Fully hands-free operation in Punjabi and English (BC distracted-driving law): spoken queries ("how much did I make today?"), spoken expense logging, spoken briefings.
