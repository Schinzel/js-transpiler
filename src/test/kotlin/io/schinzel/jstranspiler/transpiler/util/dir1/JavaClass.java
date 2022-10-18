package io.schinzel.jstranspiler.transpiler.util.dir1;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.schinzel.jstranspiler.transpiler.JsTranspiler_CompileToJavaScript;
import io.schinzel.jstranspiler.transpiler.JsTranspiler_CreateSetter;

import java.time.Instant;
import java.util.List;

@SuppressWarnings("unused")
@JsTranspiler_CompileToJavaScript
public class JavaClass {
    // 0
    public String myString;
    // 1
    @JsTranspiler_CreateSetter
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
    // 12
    @JsonIgnore
    public String mySecondString;
}


