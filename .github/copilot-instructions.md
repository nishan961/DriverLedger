# GigPilot — Copilot standing instructions

You are building **GigPilot**, an earnings, tax, and positioning co-pilot for gig drivers (Uber/Lyft/food delivery) in Metro Vancouver, Canada. The full spec lives in `docs/FEATURES.md`, `docs/DATA_SOURCES.md`, `docs/SECURITY.md`, `docs/MONETIZATION.md`, and `docs/ROADMAP.md` — read them before building any feature.

## Stack (locked)
- Kotlin + Jetpack Compose, MVVM, single-activity.
- Package: `com.gigpilot.app`. Min SDK 26, target latest.
- Local-first: Room database encrypted with SQLCipher; keys via Android Keystore. No account system in v1.
- All UI strings in string resources (the app localizes into 18 languages progressively).

## Hard rules (never violate)
1. **FORBIDDEN:** never overlay on, read screens of, scrape, or automate the Uber/Lyft/DoorDash apps — no accessibility-based reading of platform apps, ever. Platform data enters only via: driver-downloaded CSV import, driver-granted receipt-email access, or optional (off-by-default) notification-listener with on-device filtering that discards non-platform notifications immediately and never transmits notification content.
2. **Performance:** 60 fps minimum UI at all times (120 Hz where available). Lazy lists, no heavy work on the main thread, all network/DB/polling off the UI thread, GPU-light animations only.
3. **Theme:** automatic day/night — light (white) UI in daytime, true-black UI at night, switching by local sunrise/sunset; identical layouts in both; manual override Auto/Light/Dark in settings.
4. **Free only:** no paid APIs, no ad SDKs, no trackers/analytics SDKs. Live data per docs/DATA_SOURCES.md (OpenSky, BC Ferries conditions, Open-Meteo, GitHub Actions poller + FCM pushes).
5. **Security:** sensitive data stays on-device, encrypted (docs/SECURITY.md). Crowdsourced submissions leave the phone stripped of identity, zone-level location only.
6. Glanceable UI: home screen = three huge numbers (live net $/hr, today's total, goal pace) + Positioning/next-alert strip. One-thumb, zero-typing core flows. Start Shift must work within 10 seconds of first open, no onboarding interrogation.

## Build order
Follow `docs/BUILD_PLAN.md` phase by phase. Do not build later phases before earlier ones are committed and building cleanly. After each phase: ensure `./gradlew assembleDebug` succeeds.
