# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/renxianju/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-dontwarn
-ignorewarnings
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-verbose
##-dontnote

##作为library时候不能使用
-allowaccessmodification

# Optimization is turned off by default. Dex does not like code run
# through the ProGuard optimize and preverify steps (and performs some
# of these optimizations on its own).
#-dontoptimize
-dontpreverify
# Note that if you want to enable optimization, you cannot just
# include optimization flags in your own project configuration file;
# instead you will need to point to the
# "proguard-android-optimize.txt" file instead of this one from your
# project.properties file.


#如果有引用v4包可以添加下面这行
-keep class android.support.v4.** { *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment

#如果引用了v4或者v7包，可以忽略警告，因为用不到android.support
-dontwarn android.support.**

-keepclassmembers class * {
    public void *ButtonClicked(android.view.View);
}

#xUtils(保持注解，及使用注解的Activity不被混淆，不然会影响Activity中你使用注解相关的代码无法使用)
-keep class * extends java.lang.annotation.Annotation {*;}

#lvt4j
-keep class javax.**{*;}
-keep interface javax.**{*;}
-keep class com.lvt4j.**{*;}
-keep public class com.lvt4j.**{public static <fields>;}


-keepattributes *Annotation*
-keepattributes InnerClasses
-keepattributes SourceFile,LineNumberTable
-keepattributes Signature
-keepattributes EnclosingMethod
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService

# For native methods, see http://proguard.sourceforge.net/manual/examples.html#native
-keepclasseswithmembernames class * {
    native <methods>;
}

# keep setters in Views so that animations can still work.
# see http://proguard.sourceforge.net/manual/examples.html#beans
-keepclassmembers public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    void set*(***);
    *** get*();
}

#-keepclasseswithmembers class * {
#    public <init>(android.content.Context, android.util.AttributeSet);
#}
#
#-keepclasseswithmembers class * {
#    public <init>(android.content.Context, android.util.AttributeSet, int);
#}


# We want to keep methods in Activity that could be used in the XML attribute onClick
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}


-keepclassmembers class **.R$* {
    public static <fields>;
}

# Keep serializable classes and necessary members for serializable classes
# Copied from the ProGuard manual at http://proguard.sourceforge.net.
-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}


# The support library contains references to newer platform versions.
# Don't warn about those in case this app is linking against an older
# platform version.  We know about them, and they are safe.
-keep class android.support.**
-dontwarn android.support.**

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
#fix samsung menubuilder not found error
-keep class !android.support.v7.internal.view.menu.*MenuBuilder*, android.support.v7.** { *; }
-keep interface android.support.v7.** { *; }

-keep class android.support.** { *; }
-keep class com.android.** { *; }


-keep class com.google.*
-keep class java.*
-keep class javax.*
-keep interface java.*
-keep interface javax.*

-keep interface com.google.*
-keep public class * implements java.io.Serializable
-keep class com.google.gson.**
-keep interface com.google.gson.**
-keepclasseswithmembers class com.google.gson.** { <methods>; <fields>; }
-keepclasseswithmembers class com.google.gson.** { static <methods>; }
-keepclasseswithmembers class com.google.gson.** { static <fields>; }
-keepclasseswithmembers interface com.google.gson.** { <methods>; }
-keepclasseswithmembers interface com.google.gson.** { static <fields>; }

#facebook
-keep class com.facebook.**
-keep interface com.facebook.**
-keep class java.*
-keep class javax.*
-keep interface java.*
-keep interface javax.*

#for Google Play Advertising IDs and Amplitude dependencies
-keep class com.google.android.gms.ads.** { *; }
-dontwarn okio.**

#-keep class com.nineoldandroids.** { *; }
#-keep interface com.nineoldandroids.** { *; }
#-dontwarn com.nineoldandroids.**

#for Google Play Admoob ad
-keep class com.google.firebase.**{ *; }
-keep interface com.google.firebase.** { *; }
-dontwarn com.google.firebase.**

# Basic ProGuard rules for Firebase Android SDK 2.0.0+
-keep class com.firebase.** { *; }
-keep class org.apache.** { *; }
-keepnames class com.fasterxml.jackson.** { *; }
-keepnames class javax.servlet.** { *; }
-keepnames class org.ietf.jgss.** { *; }
-dontwarn org.apache.**
-dontwarn org.w3c.dom.**

#compile 'com.jaedongchicken:ytplayer:1.2.0' com.jaedongchicken.ytplayer
-keep class com.jaedongchicken.**{*;}
-dontwarn com.jaedongchicken.**

# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

# RxJava RxAndroid
-dontwarn rx.**
-keep class rx.** { *;}
-dontwarn rx.android.**
-keep class rx.android.** { *;}
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

# Gson
-keepattributes Signature
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
# Application classes that will be serialized/deserialized over Gson 下面替换成自己的实体类

#okhttp3
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}
-dontwarn okio.**
-keep class okio.**{*;}
-dontwarn javax.annotation.**
-dontwarn javax.annotation.ParametersAreNonnullByDefault

#xstream
-dontwarn com.thoughtworks.xstream.**
-keep class com.thoughtworks.xstream.** {*; }
-keep interface com.thoughtworks.xstream.** {*; }

#dom4j
-dontwarn org.dom4j.**
-keep class org.dom4j.** { *;}

#jaxen
-dontwarn org.jaxen.**
-dontwarn org.w3c.dom.**
-keep class org.jaxen.** { *;}
-keep class org.w3c.dom.** { *;}

#slf4j
-dontwarn org.slf4j.**
-keep class org.slf4j.** { *;}

-dontwarn org.xbill.DNS.**
-keep class org.xbill.DNS.** { *;}

-dontwarn com.android.volley.**
-keep class com.android.volley.** { *;}

# ormlite
-keep class com.j256.**
-keepclassmembers class com.j256.** { *; }
-keep enum com.j256.**
-keepclassmembers enum com.j256.** { *; }
-keep interface com.j256.**
-keepclassmembers interface com.j256.** { *; }
-keepclassmembers class * {
  public <init>(android.content.Context);
}

-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}