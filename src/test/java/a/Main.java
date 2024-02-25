package a;

import com.github.azbh111.ideaplugin.environmentvariable.domain.val.EnvValue;

public class Main {
    public static void main(String[] args) {
        String render = new EnvValue("\"aaa${v1}bbb${v2}aaa\\${ccc}${ddd\\}aaa} \\//asa #sss ${zz}\"")
                .render(null);
        System.out.println(render);
    }
}
