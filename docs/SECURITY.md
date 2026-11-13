# DriverLedger — Security & Privacy

Owner's rule (Nishan, 2026-11-13): **drivers' data must not get hacked.** Design goal: there should be almost nothing worth stealing, and what exists stays locked on the driver's own phone.

## Principle 1 — Local-first (the core decision)
- Trips, earnings, mileage, receipts, tax data live **on the driver's phone only**, in an **encrypted local database** (SQLCipher/Room + Android Keystore-held keys).
- No central server holds drivers' earnings data. A hacker cannot breach a database that does not exist.
- Optional app lock (biometric/PIN) on top of the phone's own lock.

## Principle 2 — Minimal cloud
- The free backend handles only *public* signals: YVR flights, BC Ferries, weather, gas/charging reports — plus anonymous push tokens and coarse zone preferences.
- **No accounts in v1** = no password database to leak, nothing to phish.
- If cloud backup is ever added: data encrypted **on the phone before upload** (key stays with the driver), HTTPS/TLS in transit, strict access rules, and backup is driver-initiated and exportable.

## Principle 3 — Crowdsourcing without exposure
- Shared reports (airport queue, gas/charging prices, conditions) are **stripped of identity** before leaving the phone: no names, no trip histories, no exact traces.
- Location is shared **coarsely** (zone-level), never a live pinpoint of a person.

## Principle 4 — Permission discipline
- Request only what a feature needs, explain each in plain language, everything sensitive is opt-in and off by default.
- Notification access (if the driver opts in): filter platform notifications **on-device**, discard everything else immediately, never transmit notification content to any server.
- Email/receipt access (if used): OAuth tokens only — never the driver's email password; revocable anytime; processing on-device where possible.

## Principle 5 — Build hygiene
- Public repo = auditable code; minimal third-party dependencies; no ad SDKs or trackers (they are the usual leak source in free apps).
- Security fixes ship through normal app updates; the backend's public-signal data is worthless to attackers by design.

## Honest limit
No system is unhackable. The defense is **minimization + encryption + no central honeypot**: worst-case exposure stays small because the valuable data never leaves the driver's pocket.
