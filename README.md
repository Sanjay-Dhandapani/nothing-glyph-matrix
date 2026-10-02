# Nothing Glyph Matrix Starter

This repository is a starter app for the Nothing Phone (4a) Pro Glyph Matrix.

What it does:

- Targets the 13x13 Matrix path for the Phone (4a) Pro
- Registers an AOD-only Glyph Toy with `DEVICE_25111p`
- Loads the Spider-man mask animation from `app/src/main/assets/spider_mask.json`
- Shows a live preview inside the app and a shortcut to the Glyph Toys manager

## What you need to run it

1. Get the official Glyph Matrix AAR from the Nothing Developer Programme GitHub repo: `Nothing-Developer-Programme/GlyphMatrix-Developer-Kit`.
2. Copy `glyph-matrix-sdk-2.0.aar` into `app/libs/`.
3. If the release asset is not visible, clone the repo or download the source ZIP from GitHub and extract the AAR from the repository root.
4. Open the project in Android Studio and sync Gradle.
5. Install the app on a Nothing Phone (4a) Pro running Android 14 or newer.
6. Use `app/src/main/assets/glyph_toy_preview.svg` when the submission form asks for an image preview.
7. Replace `app/src/main/assets/spider_mask.json` if you want a different mask animation.

## Live preview

Open `preview/index.html` through a local server to see the glyph animation in the browser.

Example:

```powershell
python -m http.server 8000 -d "D:\PROJECTS AND FILES\NOTHING GLYPH"
```

Then open `http://localhost:8000/preview/`.

## Design format

The app now consumes the exported animated JSON format directly.

- Each frame contains a `d` duration in milliseconds and a `p` pixel payload.
- The preview renders the first frame from the JSON so you can verify the animation shape quickly.

## Notes

- The manifest is already configured for the Nothing permission and the AOD toy service.
- The starter uses the `com.nothing.ketchum` SDK namespace from the official docs.
- The browser preview now maps pixel intensity into mask shading so the blinking eyes are visible on the live server.
- If you want a different design, export a new JSON file in the same format and replace `spider_mask.json`.
- The SVG preview is the image fallback for the Playground upload step when JSON is not accepted.
