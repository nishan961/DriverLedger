# GigPilot — Build Plan (phased)

Build in phases. Each phase must compile (`assembleDebug`) and be committed to `copilot-build` before the next begins. Juno verifies each phase; Nishan tests on his S24 Ultra.

## Phase A — Shell (first build)
- Project scaffold: Kotlin + Compose, MVVM, package `com.gigpilot.app`, Gradle wrapper.
- Encrypted local DB (Room + SQLCipher, Keystore keys).
- Theme system: auto day (light/white) / night (true black) by local sunrise/sunset + manual override; identical layouts.
- Home screen: three big numbers (live net $/hr, today's total, goal pace) + next-alert strip (placeholder data OK), Start/End Shift button; shift timer with per-second ticking computed locally; End Shift → Report Card screen (summary from local data).
- Home-screen widget (net $/hr + next alert placeholder).
- Settings skeleton (theme, alert lead time, nav app choice: Google Maps/Waze).
- Acceptance: installs on S24 Ultra, 60 fps scrolling/ticking, theme flips correctly, zero setup to start a shift.

## Phase B — Earnings core
- Trip + expense entry (one-thumb, minimal taps), multi-app totals (Uber/Lyft/DoorDash/other), dead-km tracking, fuel/charge logging.
- CSV statement import (Uber/Lyft/DoorDash formats) with full backfill + dedupe; annual tax summary import placeholder for reconciliation.
- Personal history views (day/week/month), live net $/hr engine (earnings − costs, incl. per-km cost setting).

## Phase C — Tax pack basics
- GPS mileage auto-log during shifts (CRA-grade log: date, purpose, km).
- HST collected tally + quarterly view; receipt photo capture stored encrypted on-device.
- Basic exports (CSV of trips/mileage — driver's own data, free forever).

## Phase D — Live boards + Comfort Map
- YVR arrivals board (schedule + OpenSky positions; bank grouping), BC Ferries conditions board (Tsawwassen/Horseshoe Bay), weather (Open-Meteo).
- Comfort Map: seeded washrooms/24h spots/rest areas + driver reports (washrooms, construction pit stops with 14-day expiry, gas & charging prices), verification counts, one-tap Navigate handoff (Maps/Waze intent).
- Curb-arrival timing on flight cards (30 min international / 20 domestic-transborder model).

## Phase E — Alerts engine + voice
- Backend poller as GitHub Actions scheduled workflow (flights/ferries/weather every ~5 min) writing alert state; FCM pushes: 40-min bank warnings, final-approach (geo-scoped), landed, ferry docking/cancellations; distinct chimes per alert family; alerts delivered during trips.
- Queue Time Truth: YVR staging-lot geofence, unpaid wait timer, honest $/hr.
- No-Show Timer.
- Voice basics (English + Punjabi): spoken queries + expense logging; Trip Check verdicts.

## Later (v2/v3 per FEATURES.md)
Tax pack completion, watchdog, dispute assistant, deactivation shield vault, Positioning Score, Tomorrow Briefing, Rain/Snow Money, Border Watch, Event Radar, Idle-Killer, Road Captain ranks, Platform Scorecard, Personal Playbook, Year in Review, language packs, family view, premium tier wiring (test builds: everything unlocked).
