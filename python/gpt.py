import openai
def openai_api(text):
        # 設定 OpenAI 憑證
    openai_api_key = "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"
    headers = {
        'Content-Type': 'application/json',
        'Authorization': f'Bearer{openai_api_key}'
    }

# OpenAI 模型回覆
    openai.api_key = openai_api_key
    # 將第六個字元之後的訊息發送給 OpenAI
    response = openai.Completion.create(
        model='text-davinci-003',
        prompt=text,
        max_tokens=256,
        temperature=0.5,
    )
    # 接收到回覆訊息後，移除換行符號
    reply_msg = response["choices"][0]["text"].replace('\n', '')
    if reply_msg:
        return reply_msg
    else:
        return '抱歉，我無法回答'