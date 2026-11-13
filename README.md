# GigPilot (repo: DriverLedger)

**An earnings, tax, and positioning co-pilot for gig drivers in Metro Vancouver, Canada.**

**Branding:** Product name **GigPilot** (chosen by Nishan, 2026-11-13). App icon: amber map pin fused with a teal steering wheel on navy — `assets/logo/gigpilot-icon.png` (logo concept #1, chosen by Nishan).

Built for Uber / Lyft / food-delivery drivers who multi-app and want their real numbers: true net $/hour, CRA-ready mileage and HST records, and live local intelligence (YVR flight banks, BC Ferries dockings, events, weather, border waits) delivered as push alerts *before* the demand wave hits.

## Why this exists
- Drivers see gross numbers in the Uber app; nobody shows them their **real** net hourly after fuel, dead kilometers, and depreciation.
- Tax season ambushes drivers every year — mileage logs, HST collected, receipts — scattered or missing.
- Demand in this region runs on two clocks — **planes (YVR)** and **boats (BC Ferries)** — and drivers play both by guesswork.

## Business model
- Free: shift & earnings tracking, live net $/hour, basic mileage log.
- Premium (~$6.99/mo): tax pack (CRA mileage log, HST tracker, receipt capture, accountant export), multi-app dashboard, heatmaps, positioning score, smart alerts.
- Growth: invite rewards (driver invites driver), anonymous local earnings benchmarks.

## Roles (locked)
- **Nishan** — project owner, domain expert (12h/day Uber driver, Surrey BC), device tester.
- **GitHub's AI (Copilot)** — writes ALL application code.
- **Juno (Muse)** — repository setup and management, project structure, prompts for the builder AI, verification, delivery of finished APKs. Juno does not write app code.

## Structure
- `docs/FEATURES.md` — the full feature specification (waves 1–3).
- `docs/ROADMAP.md` — v1 / v2 / v3 phasing.
- `app/` — application code (built by GitHub's AI on the build branch).

## Branches
- `main` — stable specs and releases.
- `copilot-build` — active construction branch for GitHub's AI.
