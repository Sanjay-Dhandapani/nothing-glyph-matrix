# Nothing Glyph Matrix Starter

This repository is a starter app for the Nothing Phone (4a) Pro Glyph Matrix.

What it does:

- Targets the 13x13 Matrix path for the Phone (4a) Pro
- Registers an AOD-only Glyph Toy with `DEVICE_25111p`
- Loads the Spider-man animation from `app/src/main/assets/spider_man.json`
- Shows a live preview inside the app and a shortcut to the Glyph Toys manager

## What you need to run it

1. Get the official Glyph Matrix AAR from the Nothing Developer Programme GitHub repo: `Nothing-Developer-Programme/GlyphMatrix-Developer-Kit`.
2. Copy `glyph-matrix-sdk-2.0.aar` into `app/libs/`.
3. If the release asset is not visible, clone the repo or download the source ZIP from GitHub and extract the AAR from the repository root.
4. Open the project in Android Studio and sync Gradle.
5. Install the app on a Nothing Phone (4a) Pro running Android 14 or newer.
6. Replace `app/src/main/assets/spider_man.json` if you want a different animation.

## Design format

The app now consumes the exported animated JSON format directly.

- Each frame contains a `d` duration in milliseconds and a `p` pixel payload.
- The preview renders the first frame from the JSON so you can verify the animation shape quickly.

## Notes

- The manifest is already configured for the Nothing permission and the AOD toy service.
- The starter uses the `com.nothing.ketchum` SDK namespace from the official docs.
- If you want a different design, export a new JSON file in the same format and replace `spider_man.json`.
