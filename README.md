# GearSlider
GearSlider UI for Android - It was inspired by the Adjust UI of Instagram.

# Sample Project
preparing..

# Gradle Dependency (jCenter)

Easily reference the library in your Android projects using this dependency in your module's `build.gradle` file:

```Gradle
dependencies {
    compile 'org.nhnnext.sunghwanjo:gearslider:0.1.7'
}
```

---

# Simple Usage

Add res-auto
```xml
xmlns:gs="http://schemas.android.com/apk/res-auto"
```

```xml
    <org.nhnnext.josunghwan.gearslider.GearSlider
        android:layout_width="match_parent"
        android:layout_height="75dp"
        gs:background_color="@android:color/black"
        gs:bar_color="@android:color/white"
        gs:height_of_bar="37dp"
        gs:height_of_longbar="50dp"
        gs:centerbar_color="@android:color/holo_green_dark"
        gs:init_value="0"
        gs:interval_of_bar="20dp"
        gs:number_of_bar="50"
        gs:interval_of_longbar="5"
        gs:on_fling="true"
        gs:fling_max_value="50"
        gs:fling_min_value="10"
        />
```

#Thanks
Ji YongJoo
