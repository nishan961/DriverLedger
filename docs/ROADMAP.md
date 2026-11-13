# DriverLedger — Roadmap

## Build constraints (locked by Nishan, 2026-11-13)
- **$0 build cost.** Free tiers and open data only — no paid APIs, no paid services at any stage of building or testing.
- **v1 / v2 / v3 below is BUILD ORDER, not a paywall.** The test build ships with ALL features unlocked.
- **Nishan personally tests the full build** on his S24 Ultra across several days of real shifts before any public release — and before any premium tier is switched on for other drivers.


## v1 (ship this first)
- Shift tracking + live net $/hour
- Multi-app earnings totals (manual/quick entry per platform)
- CRA mileage log + HST tracker
- Receipt photo capture
- Statement/trip-history CSV import (year backfill)
- YVR arrivals board + ferry live conditions (view-only)
- Punjabi + English UI

## v2
- Tax pack complete: set-aside meter, accountant export, annual tax summary import
- Push alerts: flight banks (40-min lead), ferry dockings, cancellations
- Pay-check watchdog (BC engaged-time minimum) + fare dispute assistant
- Personal heatmap + best-start advisor
- Goal mode, maintenance & renewal reminders
- Energy radar (gas crowdsourcing; EV charging reports)

## v3 — the smart layer
- Positioning Score
- Tomorrow Briefing
- Rain Money, Border Watch, Event radar fusion
- Idle-Killer repositioning suggestions
- Punjabi Voice Co-Pilot (hands-free)
- Surrey benchmark (anonymous local averages) + invite rewards
- Family view

## Platforms (noted by Nishan, 2026-11-13)
- **Android first** — owner's S24 Ultra, driver-community work phones, $0 build + one-time $25 Play fee at public launch.
- **iOS second (post-launch phase)** — most Canadians carry iPhones; GigPilot's backend, data feeds, and accounts design are platform-neutral so an iOS build reuses everything except the app shell. Buildable without owning a Mac via GitHub Actions macOS runners (free for public repos). Costs at that stage: Apple Developer $99 USD/year; stricter review.
- **Known iOS gap:** Android's notification-listener auto-logging has no iOS equivalent — iOS relies on imports + receipt email. Acceptable; flagged honestly in advance.
