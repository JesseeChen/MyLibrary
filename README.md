# RatioLayout
This is a customer viewgroup which layouted child by ratio in hori and vertical. It has three properties:"x_ratio(float)","y_ratio(float)" and "can_drag(boolean)".
Property "x_ratio" and "y_ratio" decide the  child location on hori and vertical direction by ratio and property "can_drag" decide if child can move it's location while touch screen.
eg.when you set "x_ratio" to 0.5f and "y_ratio" to 0.5f,child will layout in center of his parent.

# How to add it to your project
* maven
```
<dependency>
  <groupId>com.jesse.library</groupId>
  <artifactId>lib-ratiolayout</artifactId>
  <version>1.1</version>
  <type>pom</type>
</dependency>
```

* gradle
```

repositories{
    maven{
        url 'https://dl.bintray.com/jesscheen/maven'
    }
}

dependencies{
  compile 'com.jesse.library:lib-ratiolayout:1.1'
}
  ```
