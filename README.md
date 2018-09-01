# 2DCollisionUtils

A simple WIP CollisionUtils class, that provides basic collision detection functions for 2D using only primitive Java Types.

This util class is supposed to be used with a custom adapter that translates the classes used for the description of collision objects into primitives. The point (0,0) is always assumed to be at the bottom left and objects should already be scaled and rotated if applicable.

#### Maven

```xml
<dependency>
  <groupId>com.github.mrcdnk</groupId>
  <artifactId>2d-collision-utils</artifactId>
  <version>0.1.0</version>
</dependency>
```

#### Gradle

```groovy
  dependencies { compile "com.github.mrcdnk:2d-collision-utils:0.1.0" }
```