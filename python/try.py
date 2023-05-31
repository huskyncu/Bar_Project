from snownlp import SnowNLP

text = "貴的"
text2 = "不要"
s = SnowNLP(text)
s2 = SnowNLP(text2)
print(s.sentiments,s2.sentiments)