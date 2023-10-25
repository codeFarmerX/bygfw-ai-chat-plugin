package org.bgyfw.plugin.resp;

import java.util.List;

public class CompletionsResp {

    /**
     * id : chatcmpl-3sAC99CVr3WFkL9BbGBwUb
     * object : chat.completion
     * created : 1697695500
     * model : qwen-7b
     * choices : [{"index":0,"message":{"role":"assistant","content":"I am a large language model created by DAMO Academy. I am called QianWen."},"finish_reason":"stop"}]
     * usage : {"prompt_tokens":22,"total_tokens":42,"completion_tokens":20}
     */

    private String id;
    private String object;
    private int created;
    private String model;
    private UsageBean usage;
    private List<ChoicesBean> choices;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public int getCreated() {
        return created;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public UsageBean getUsage() {
        return usage;
    }

    public void setUsage(UsageBean usage) {
        this.usage = usage;
    }

    public List<ChoicesBean> getChoices() {
        return choices;
    }

    public void setChoices(List<ChoicesBean> choices) {
        this.choices = choices;
    }

    public static class UsageBean {
        /**
         * prompt_tokens : 22
         * total_tokens : 42
         * completion_tokens : 20
         */

        private int prompt_tokens;
        private int total_tokens;
        private int completion_tokens;

        public int getPrompt_tokens() {
            return prompt_tokens;
        }

        public void setPrompt_tokens(int prompt_tokens) {
            this.prompt_tokens = prompt_tokens;
        }

        public int getTotal_tokens() {
            return total_tokens;
        }

        public void setTotal_tokens(int total_tokens) {
            this.total_tokens = total_tokens;
        }

        public int getCompletion_tokens() {
            return completion_tokens;
        }

        public void setCompletion_tokens(int completion_tokens) {
            this.completion_tokens = completion_tokens;
        }
    }

    public static class ChoicesBean {
        /**
         * index : 0
         * message : {"role":"assistant","content":"I am a large language model created by DAMO Academy. I am called QianWen."}
         * finish_reason : stop
         */

        private int index;
        private MessageBean message;
        private String finish_reason;

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public MessageBean getMessage() {
            return message;
        }

        public void setMessage(MessageBean message) {
            this.message = message;
        }

        public String getFinish_reason() {
            return finish_reason;
        }

        public void setFinish_reason(String finish_reason) {
            this.finish_reason = finish_reason;
        }

        public static class MessageBean {
            /**
             * role : assistant
             * content : I am a large language model created by DAMO Academy. I am called QianWen.
             */

            private String role;
            private String content;

            public String getRole() {
                return role;
            }

            public void setRole(String role) {
                this.role = role;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}
