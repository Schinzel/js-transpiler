package io.schinzel.jstranspiler.transpiler.util;

import java.time.Instant;
import java.util.List;

@SuppressWarnings("unused")
public class JavaClass {
    // 0
    public String myString;
    // 1
    public int myInt;
    // 2
    public Integer myInteger;
    // 3
    public boolean myPrimitiveBoolean;
    // 4
    public Boolean myBoolean;
    // 5
    public Instant myInstant;
    // 6
    MyOtherJavaClass myClass;
    // 7
    List<String> myStringList;
    // 8
    List<MyOtherJavaClass> myMyOtherJavaClassList;
    // 9
    JavaEnum myJavaEnum;
    // 10
    List<JavaEnum> myJavaEnumList;
    // 11
    List<Integer> myIntegerList;
}


