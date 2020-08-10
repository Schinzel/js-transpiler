package io.schinzel.jstranspiler.transpiler.util;

import java.time.Instant;
import java.util.List;

@SuppressWarnings("unused")
public class JavaClass {
    public String myString;
    public int myInt;
    public Integer myInteger;
    public boolean myPrimitiveBoolean;
    public Boolean myBoolean;
    public Instant myInstant;
    MyOtherJavaClass myClass;
    List<String> myStringList;
    List<MyOtherJavaClass> myMyOtherJavaClassList;
    JavaEnum myJavaEnum;
   List<JavaEnum> myJavaEnumList;
}


