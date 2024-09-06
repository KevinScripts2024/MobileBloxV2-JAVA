# MobileBloxV2-JAVA

MobileBloxV2-JAVA is an Android application developed using Visual Studio, designed to load and interact with the native `MobileBloxV2` library. This project demonstrates runtime permission handling, native library integration, and basic Android lifecycle management.

## Features

- Loads `MobileBloxV2` native library on startup.
- Requests essential runtime permissions for file storage and media access.
- Displays a UI message when the library is loaded.
- Shows Toast messages indicating app initiation and permission status.
- Provides clipboard management and HWID retrieval functionality via JNI.

## Prerequisites

- Visual Studio with Android development tools
- Native `MobileBloxV2` library (Ensure it is properly linked and compiled for ARM64)
- Android device or emulator

## Permissions

The following permissions are required and handled at runtime:

- `MANAGE_EXTERNAL_STORAGE`
- `ACCESS_MEDIA_LOCATION`
- `WRITE_EXTERNAL_STORAGE`
- `READ_MEDIA_IMAGES`

These permissions are declared in the `AndroidManifest.xml` and requested during app startup.

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/literallyawildhog/MobileBloxV2-JAVA.git
   ```

2. Open the project in **Visual Studio**.

3. Build the project and install it on your Android device.

## Usage

When the app is launched, it will:

- Load the `MobileBloxV2` library using `System.loadLibrary("MobileBloxV2")`.
- Request necessary permissions.
- Display a "MobileBloxV2 library loaded." message on the screen.
- Show a Toast message: "MobileBlox initiated."

If permissions are granted or denied, appropriate Toast messages will notify the user.

## JNI Functions

The `MobileBloxV2` library provides the following JNI functions:

### `setClipboard(String data)`

Sets the specified text to the clipboard.

### `getClipboard()`

Retrieves the current text from the clipboard.

### `getHwid()`

Returns the Android device’s unique hardware ID.

## Adding `com.MobileBlox` to Other APKs' Smali Files

To integrate `MobileBloxV2-JAVA` into other APKs using smali, follow these steps:

1. **Decompile the Target APK:**
   Use `apktool` to decompile the APK:
   ```bash
   apktool d target.apk
   ```

2. **Add the `com.MobileBlox` Package:**
   - Navigate to the `smali` folder in the decompiled APK.
   - Copy the `com/MobileBlox` package from the `MobileBloxV2-JAVA` project into the `smali` folder of the target APK.

3. **Modify the Smali Files:**
   - Open the `smali` files in the target APK where you want to call the `MobileBloxV2` functionality.
   - Add a reference to load the `MobileBloxV2` library in your smali file. For example:
     ```smali
     invoke-static {}, Lcom/MobileBlox/MobileBlox;->loadLibrary()V
     ```

4. **Ensure ARM64 Library Integration:**
   - Make sure the `MobileBloxV2` library includes an ARM64 version. Typically, you should place the ARM64 shared library (`libMobileBloxV2.so`) in the appropriate folder, e.g., `libs/arm64-v8a/`.
   - Verify that your APK’s build includes the ARM64 libraries by checking the `build.gradle` file or the APK structure.

5. **Recompile and Sign the APK:**
   After modifying the smali files, recompile the APK:
   ```bash
   apktool b target_folder
   ```
   Sign the APK before installation:
   ```bash
   jarsigner -keystore my-release-key.keystore target.apk alias_name
   ```

6. **Install the Modified APK:**
   Install the APK on your Android device to verify that the `MobileBloxV2` functionality is integrated.

## License

This project is licensed under the MIT License.
