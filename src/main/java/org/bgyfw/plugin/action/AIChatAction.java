package org.bgyfw.plugin.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.bgyfw.plugin.Config;

public class AIChatAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Config.chatForm.getToolWindow().show(null);
    }
}
