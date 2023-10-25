package org.bgyfw.plugin.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import org.apache.commons.lang.StringUtils;
import org.bgyfw.plugin.Config;
import org.bgyfw.plugin.form.ChatForm;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class ExplainCodeAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // 获取用户当前所在的编辑器对象
        Editor editor = e.getData(PlatformDataKeys.EDITOR);

        // 通过编辑器对象获取用户的选择对象
        SelectionModel selectionModel = editor.getSelectionModel();

        // 获取选中的文本信息
        String selectText = selectionModel.getSelectedText();
        if(StringUtils.isEmpty(selectText)){
            return;
        }
        Config.chatForm.getToolWindow().show(null);
        String ext = new String("请解释这段代码".getBytes(), StandardCharsets.UTF_8);
        selectText = selectText + "\n" + ext;

        Config.chatForm.completionsRequest(selectText);
    }
}
