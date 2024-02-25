package com.github.azbh111.ideaplugin.environmentvariable.domain.val;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnvValue {
    private static final Pattern p = Pattern.compile("(\\\\.|//|#|\\$\\{.+?(?<!\\\\)})");
    private final String originValue;

    public EnvValue(String originValue) {
        if (originValue == null) {
            originValue = "";
        } else {
            originValue = originValue.trim();
        }
        if (originValue.startsWith("\"") && originValue.endsWith("\"")) {
            originValue = originValue.substring(1, originValue.length() - 1);
        } else if (originValue.startsWith("'") && originValue.endsWith("'")) {
            originValue = originValue.substring(1, originValue.length() - 1);
        }
        this.originValue = originValue;
    }

    public String render(Map<String, String> ctx) {
        if (ctx == null) {
            ctx = new HashMap<>(0);
        }
        StringBuffer sb = new StringBuffer();
        Matcher matcher = p.matcher(originValue);
        boolean appendTail = true;
        while (matcher.find()) {
            String group = matcher.group();
            if (group.startsWith("\\")) {
                matcher.appendReplacement(sb, Matcher.quoteReplacement(group.substring(1)));
            } else if (group.startsWith("$")) {
                String origin = group;
                group = group.substring(2, group.length() - 1).trim();
                String key = group;
                String defaultValue = origin;
                int dot = group.indexOf(":");
                if (dot != -1) {
                    key = group.substring(0, dot).trim();
                    defaultValue = group.substring(dot + 1).trim();
                }
                String value = ctx.get(key);
                if (value != null) {
                    matcher.appendReplacement(sb, Matcher.quoteReplacement(value));
                } else {
                    matcher.appendReplacement(sb, Matcher.quoteReplacement(defaultValue));
                }
            } else {
                matcher.appendReplacement(sb, "");
                appendTail = false;
                break;
            }
        }
        if (appendTail) {
            matcher.appendTail(sb);
        }
        return sb.toString();
    }
}
