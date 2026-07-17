# NEET CBT – Native Android App

A **100% native Android application** in **Kotlin + Jetpack Compose** replicating the NTA NEET Computer-Based Test (CBT) interface.

## 📋 Features

- **All 180 questions** from NEET Re-Exam 2026, Code 70 — hardcoded, no PDF parsing needed
- **Full answer key** embedded (NTA Final Answer Key dated 16-07-2026)
- **Pixel-perfect NTA CBT UI** matching the official NTA interface
- **Landscape orientation** forced via Manifest
- **Countdown timer** (3 hrs 15 min) with auto-submit
- **Question palette** with color-coded statuses (Not Visited / Not Answered / Answered / Marked / Answered+Marked)
- **Section tabs**: Physics | Chemistry | Botany | Zoology
- **Scoring**: +4 correct, −1 wrong, 0 unattempted
- **Q40 dropped** by NTA — full marks (+4) awarded automatically
- **Result screen** with scorecard and question-by-question analysis
- **PDF page rendering** for questions with figures (uses Android's built-in `PdfRenderer`)

---

## 🏗️ Project Structure

```
neet-cbt-app/
├── app/
│   ├── build.gradle.kts              # Module-level build config
│   └── src/main/
│       ├── AndroidManifest.xml       # Landscape orientation, no action bar
│       ├── java/com/neet/cbt/
│       │   ├── MainActivity.kt       # Entry point + screen navigation
│       │   ├── data/
│       │   │   ├── ExamData.kt       # Data classes + scoring logic
│       │   │   └── Questions.kt      # All 180 NEET questions + answers
│       │   ├── viewmodel/
│       │   │   └── ExamViewModel.kt  # State, timer, navigation, PDF rendering
│       │   └── ui/
│       │       ├── NTAHeader.kt      # Shared NTA top bar
│       │       ├── LoginScreen.kt    # Login (Demo) screen
│       │       ├── InstructionScreen.kt  # General Instructions
│       │       ├── ExamScreen.kt     # Main exam with question + palette
│       │       ├── SummaryScreen.kt  # Submit confirmation with summary
│       │       ├── ResultScreen.kt   # Scorecard + question-wise analysis
│       │       ├── theme/
│       │       │   ├── Color.kt      # NTA brand colors
│       │       │   ├── Theme.kt      # Material3 theme
│       │       │   └── Type.kt       # Typography
│       │       └── components/
│       │           ├── QuestionPalette.kt  # Right-side palette grid
│       │           ├── BottomActionBar.kt  # Action buttons
│       │           └── TimerDisplay.kt     # Countdown timer widget
│       └── res/
│           └── values/
│               ├── strings.xml
│               ├── colors.xml
│               └── themes.xml
├── build.gradle.kts                  # Project-level build config
├── settings.gradle.kts
└── gradle/
    ├── libs.versions.toml            # Version catalog
    └── wrapper/
        └── gradle-wrapper.properties
```

---

## 🚀 Build Instructions

### Prerequisites

| Tool | Version |
|------|---------|
| Android Studio | Ladybug (2024.2) or newer |
| JDK | 17 or 21 |
| Android SDK | API 34 (Target), API 24+ (Min) |
| Gradle | 8.7 (via wrapper) |

### Steps

1. **Clone / copy** the `neet-cbt-app/` directory to your machine.

2. **Open in Android Studio**:
   - File → Open → select the `neet-cbt-app/` folder
   - Wait for Gradle sync to complete

3. **Build APK**:
   ```bash
   ./gradlew assembleDebug
   ```
   The APK will be at:
   ```
   app/build/outputs/apk/debug/app-debug.apk
   ```

4. **Install on device / emulator**:
   ```bash
   ./gradlew installDebug
   ```
   Or in Android Studio: Run → Run 'app'

5. **Build release APK** (requires signing):
   ```bash
   ./gradlew assembleRelease
   ```

---

## 📱 App Flow

```
Login Screen
    ↓ (click LOGIN)
Instructions Screen
    ↓ (accept declaration + click "I am ready to begin")
Exam Screen ←→ (navigate questions, palette, sections)
    ↓ (click SUBMIT)
Exam Summary Popup
    ↓ (click YES)
Result Screen (scorecard + question-wise analysis)
```

---

## 🎨 UI Color Guide

| Color | Hex | Meaning |
|-------|-----|---------|
| Grey | `#D1D5DB` | Not Visited |
| Red | `#EF4444` | Not Answered |
| Green | `#22C55E` | Answered |
| Purple | `#8B5CF6` | Marked for Review |
| Dark Purple + Green dot | `#7C3AED` | Answered & Marked |

---

## ⌨️ Button Actions

| Button | Action |
|--------|--------|
| SAVE & NEXT | Saves option → ANSWERED → moves to next question |
| CLEAR | Removes saved answer → NOT_ANSWERED, stays |
| SAVE & MARK FOR REVIEW | Saves + marks → ANSWERED_AND_MARKED, stays |
| MARK FOR REVIEW & NEXT | Marks → ANSWERED_AND_MARKED → next |
| << BACK | Previous question (no save) |
| NEXT >> | Next question (no save) |
| Palette number | Jump to question (no save) |
| SUBMIT | Opens summary dialog |

---

## 📊 Scoring

```
Correct answer  → +4 marks
Wrong answer    → −1 mark  
Not attempted   → 0 marks
Dropped (Q40)   → +4 marks (bonus to all)
Maximum score   → 720 marks (180 × 4)
```

---

## 🔧 Key Technical Details

- **Min SDK**: API 24 (Android 7.0 Nougat)
- **Target SDK**: API 34 (Android 14)
- **Orientation**: Landscape forced via `android:screenOrientation="sensorLandscape"`
- **Architecture**: MVVM with `ViewModel` + `StateFlow` + Jetpack Compose
- **PDF Rendering**: Android's built-in `PdfRenderer` (no third-party library needed)
- **Persistence**: `ExamViewModel` holds all state in memory (session-based)
- **No internet required** — fully offline after install

---

## 📝 Notes

- Questions with figures display a placeholder or a rendered PDF page bitmap. To enable actual rendering, place the question paper PDF at the app's files directory and call `vm.setPdfFile(file)` from `MainActivity` after loading.
- Question 40 was officially dropped by NTA — the app automatically awards full marks (+4) to all students for it.
- Question 22 has two officially incorrect answers (C and D per NTA key); the app treats option C (index 2) as correct.
- The Chemistry section (Q46–90) uses options numbered (1)(2)(3)(4) in the original paper; these map to A/B/C/D internally.
