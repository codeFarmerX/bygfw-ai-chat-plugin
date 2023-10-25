package org.bgyfw.plugin;

import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.bgyfw.plugin.form.ChatForm;
import org.jetbrains.annotations.NotNull;

public class AiChatToolWindowFactory implements ToolWindowFactory, DumbAware {
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        ChatForm chatForm = new ChatForm(toolWindow);
        Content content = ContentFactory.SERVICE.getInstance().createContent(chatForm.getPanel1(), "", false);
        toolWindow.getContentManager().addContent(content);
        Config.chatForm = chatForm;
    }
}
