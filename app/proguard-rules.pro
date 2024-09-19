# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Retrofit
-keepattributes Signature
-keepattributes Exceptions

# Retrofit interfaces
-keep,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# Retrofit method annotations
-keepattributes Annotation

# OkHttp
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**

# Gson
#-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }

# Gson annotations
-keepattributes EnclosingMethod
-keepattributes InnerClasses
-keepattributes Signature
-keepattributes *Annotation*
-keepattributes Exceptions

# Your model classes
-keep class lagingoding.quizgame.data.remote.model.** { *; }

# Retrofit
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn retrofit2.PlatformTypes

# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
-keepattributes *Annotation*

# Keep class Retrofit and related classes for reflection
-keep class retrofit2.** { *; }
-keep class com.squareup.okhttp3.** { *; }
-keepclassmembers,allowobfuscation interface retrofit2.** {
    @retrofit2.http.* <methods>;
}

# OkHttp
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn javax.annotation.Nullable
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement


# Allow serialization/deserialization in Gson
-keepattributes *Annotation*
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# Allow Serializable and Parcelable on model classes
-keep class * implements java.io.Serializable { *; }
-keep class * implements android.os.Parcelable { *; }

# Ignore warnings about missing serialVersionUID
-keepclassmembers,allowobfuscation class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# Prevent obfuscation of Google Auth classes
-keep class com.google.api.client.googleapis.auth.oauth2.** { *; }
-keep class com.google.api.client.http.** { *; }
-keep class com.google.api.client.json.** { *; }

# Keep classes related to authentication and network requests
-keep class com.google.api.services.sheets.** { *; }
-keep class com.google.auth.** { *; }

