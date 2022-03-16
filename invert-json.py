import json

f = open('meowcorp-npc-medarot-list.json', encoding="utf-8", mode='r')
medarots = json.load(f)

new_list = {}

for m in medarots:
        new_list[m["Model"]] = m

w = open('meowcorp-npc-inverted.json', encoding='utf-8', mode='w+')
json.dump(new_list, w)
