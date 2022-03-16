import re

f = open('model-list.txt', 'r')
data = f.read()
data = data.splitlines()

#print(data)

alphabet = ['A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z']

d_size = len(data)
a_size = len(alphabet)

parts = [ [] for i in range(a_size) ]

selected_letter = alphabet[0]
letter_index = 0

for i in range(d_size):
    for j in range(a_size):
        if (data[i][0] == alphabet[j]):
            parts[j].append(data[i])
            break

for i in range(a_size):
    if len(parts[i]) > 0:
        w = open(alphabet[i] + '.txt', 'w')
        w.write('\n'.join(parts[i]))
    w.close()
