package com.github.azbh111.ideaplugin.environmentvariable.utils;

import com.github.azbh111.ideaplugin.environmentvariable.compatibility.IntellijCompatibility;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.ProgramRunner;
import com.intellij.execution.ui.RunContentDescriptor;
import org.jetbrains.annotations.NotNull;

public class NodeJsUtils {
    public static void addOnceCallback(@NotNull ExecutionEnvironment environment, @NotNull ProgramRunner.Callback callback) {
        ProgramRunner.Callback exist = environment.getCallback();
        CallbackWrapper wrapper = new CallbackWrapper(exist, callback,
            () -> IntellijCompatibility.setCallback(environment, exist));
        IntellijCompatibility.setCallback(environment, wrapper);
    }


    private static class CallbackWrapper implements ProgramRunner.Callback {
        private final ProgramRunner.Callback pre;
        private final ProgramRunner.Callback callback;
        private final Runnable post;

        private CallbackWrapper(ProgramRunner.Callback pre, ProgramRunner.Callback callback, Runnable post) {
            this.pre = pre;
            this.callback = callback;
            this.post = post;
        }

        @Override
        public void processStarted(RunContentDescriptor runContentDescriptor) {
            try {
                if (pre != null) {
                    pre.processStarted(runContentDescriptor);
                }
            } finally {
                try {
                    callback.processStarted(runContentDescriptor);
                } finally {
                    if (post != null) {
                        post.run();
                    }
                }
            }
        }

        // 不加 @Override，兼容v<=223，父类没有提供这个方法
        public void processNotStarted() {
            try {
                if (pre != null) {
                    IntellijCompatibility.processNotStarted(pre);
                }
            } finally {
                try {
                    IntellijCompatibility.processNotStarted(pre);
                } finally {
                    if (post != null) {
                        post.run();
                    }
                }
            }
        }
    }
}
