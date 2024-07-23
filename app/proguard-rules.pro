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
-keep class androidx.compose.** { *; }
-keep @androidx.compose.runtime.Composable class * {
    <methods>;
}

-keep class * extends androidx.lifecycle.ViewModel {
    <init>(...);
}

-keep class com.example.usermanager.data.model.** { *;}
-keep class com.example.usermanager.data.mapper.** { *;}
-keep class com.example.usermanager.data.network.** { *;}
-keep class com.example.usermanager.data.reposirory.** { *;}
-keep class com.example.usermanager.data.** { *;}

-keep class com.example.usermanager.domain.model.** { *;}
-keep class com.example.usermanager.domain.repository.** { *;}
-keep class com.example.usermanager.domain.usecase.adduser.** { *;}
-keep class com.example.usermanager.domain.usecase.deleteUser.** { *;}
-keep class com.example.usermanager.domain.usecase.getusers.** { *;}
-keep class com.example.usermanager.domain.** { *;}

-keep class com.example.usermanager.framework.di.** { *;}
-keep class com.example.usermanager.presentation.viewmodel.** { *;}
-keep class com.example.usermanager.presentation.components.** { *;}
-keep class com.example.usermanager.presentation.intent.** { *;}
-keep class com.example.usermanager.presentation.ui.** { *;}
-keep class com.example.usermanager.presentation.UsersUIState.** { *;}

-keep class com.example.usermanager.MainActivity {
    public <fields>;
    public <methods>;
}

-keep class com.example.usermanager.data.model.** {
 private <fields>;
}

-keep class com.example.usermanager.domain.model.** {
 private <fields>;
}