package com.github.azbh111.ideaplugin.environmentvariable.utils;

import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.ProgramRunner;
import com.intellij.execution.ui.RunContentDescriptor;
import org.jetbrains.annotations.NotNull;

public class NodeJsUtils {
    public static void addOnceCallback(@NotNull ExecutionEnvironment environment, @NotNull ProgramRunner.Callback callback) {
        ProgramRunner.Callback exist = environment.getCallback();
        CallbackWrapper wrapper = new CallbackWrapper(exist, callback, () -> environment.setCallback(exist));
        environment.setCallback(wrapper);
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

//        @Override
//        public void processNotStarted() {
//            try {
//                if (pre != null) {
//                    pre.processNotStarted();
//                }
//            } finally {
//                try {
//                    callback.processNotStarted();
//                } finally {
//                    if (post != null) {
//                        post.run();
//                    }
//                }
//            }
//        }
    }
}
