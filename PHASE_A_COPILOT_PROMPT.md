# GigPilot — Phase A prompt to paste into Copilot Chat

Prepared 2026-11-13 for Nishan. The repo-side kickoff is already live:
branch `copilot-build` on https://github.com/nishan961/DriverLedger, with
`.github/copilot-instructions.md`, `docs/BUILD_PLAN.md`, `.github/workflows/build-apk.yml`,
and issue #1 (Build Phase A) created 2026-11-13T14:33:19Z.

## Steps for Nishan
1. Open https://github.com/nishan961/DriverLedger — switch to branch `copilot-build`.
2. Open your Codespace on that branch (same way as the wallpaper build).
3. Open Copilot Chat in the Codespace and paste the prompt below.
4. Let it build, commit and push to `copilot-build`. Then tell Juno — Juno fetches
   the APK from the GitHub Actions build and delivers it in chat.

## The prompt (paste exactly this)

Build Phase A of the GigPilot Android app, exactly as in GitHub issue #1
(nishan961/DriverLedger) and docs/BUILD_PLAN.md Phase A. First read
.github/copilot-instructions.md and docs/FEATURES.md, docs/DATA_SOURCES.md,
docs/SECURITY.md, docs/MONETIZATION.md, docs/ROADMAP.md.

Scaffold: Kotlin + Jetpack Compose, MVVM, single-activity, package
com.gigpilot.app, min SDK 26, Gradle wrapper. Encrypted local storage:
Room + SQLCipher, keys via Android Keystore. No accounts, no network calls
in this phase.

Theme: automatic light (white) UI in daytime and true-black UI at night,
switching by local sunrise/sunset; identical layouts in both; settings
override Auto / Always light / Always dark.

Home screen: three large numbers (live net $/hr, today's total, goal pace —
placeholder/local data acceptable this phase), a next-alert strip
(placeholder), Start Shift / End Shift. Shift timer ticks every second from
local computation. End Shift opens a Report Card screen summarizing the shift
from local data.

Home-screen widget showing net $/hr and next alert (placeholder data
acceptable). Settings skeleton: theme override, alert lead time (20/40/60),
navigation app choice (Google Maps / Waze). All UI text in string resources
(localization-ready).

Hard rules: 60 fps minimum UI (lazy lists, no heavy work on the main thread),
zero setup before first Start Shift, NEVER overlay on, read screens of,
scrape, or automate the Uber/Lyft/DoorDash apps, no ad or tracker SDKs,
no paid APIs. Finish when ./gradlew assembleDebug succeeds. Commit to
branch copilot-build.

## Known blocker (do not work around it in the prompt)
GitHub's autonomous Copilot coding agent is NOT enabled on Nishan's account,
so the code must be written inside his own Codespace/Copilot Chat session.
That is why this paste step is needed — Juno cannot type into his Copilot session.
