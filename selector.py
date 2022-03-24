"""
@author Omitsu Demon
"""

import sqlite3, json
import time

start = time.time()

meowcorp_file = open('meowcorp-medarot-list-inverted.json', encoding="utf8")
meowcorp_data = json.load(meowcorp_file)
print("Meowcorp medarot data: OK " + str(len(meowcorp_data)))

npc_file = open('meowcorp-npc-medarot-list-inverted.json', encoding="utf8")
npc_data = json.load(npc_file)
npc_keys = npc_data.keys()
print("Meowcorp npc data: OK " + str(len(npc_keys)))

heads_file = open('heads.json', encoding="utf8")
heads_data = json.load(heads_file)
print("PRB heads data: OK")

r_arms_file = open('right-arms.json', encoding="utf8")
r_arms_data = json.load(r_arms_file)
print("PRB rarms data: OK")

l_arms_file = open('left-arms.json', encoding="utf8")
l_arms_data = json.load(l_arms_file)
print("PRB larms data: OK")

legs_file = open('legs.json', encoding="utf8")
legs_data = json.load(legs_file)
print("PRB legs data: OK")

connection = sqlite3.connect('medarot-s.db')
cursor = connection.cursor()
print("Database connection: OK")

cursor.execute('drop table right_arm')
cursor.execute('drop table left_arm')
cursor.execute('drop table leg')
cursor.execute('drop table head')

end = time.time()

print("Data loaded in: ",)
print(end-start)


def createDB():
    cursor.execute(
        '''CREATE TABLE IF NOT EXISTS right_arm
        (
            model text,
            id int,
            name text,
            armor int,
            success int,
            power int,
            heating int,
            cooldown int,
            hv int,
            ability_type text,
            ability_name text,
            ability text,
            rank_5 text,
            gender text,
            obtained text
        )
        '''
    )

    cursor.execute(
        '''CREATE TABLE IF NOT EXISTS left_arm
        (
            model text,
            id int,
            name text,
            armor int,
            success int,
            power int,
            heating int,
            cooldown int,
            hv int,
            ability_type text,
            ability_name text,
            ability text,
            rank_5 text,
            gender text,
            obtained text
        )
        '''
    )

    cursor.execute(
        '''CREATE TABLE IF NOT EXISTS leg
        (
            model text,
            id int,
            name text,
            type text,
            armor int,
            anti_melee int,
            anti_status int,
            anti_ranged int,
            base_speed int,
            hv int,
            ability_name text,
            ability text,
            rank_5 text,
            gender text,
            obtained text
        )
        '''
    )

    cursor.execute(
        '''CREATE TABLE IF NOT EXISTS head
        (
            model text,
            id int,
            name text,
            armor int,
            success int,
            power int,
            heating int,
            cooldown int,
            ammo int,
            hv int,
            ability_type text,
            ability_name text,
            ability text,
            rank_5 text,
            gender text,
            obtained text
        )
        '''
    )
    connection.commit()

def insertRARMS(data):
    tuples = [
        (
        r_arm["Model"], 
        r_arm["ID"],
        r_arm["Name"],
        int(r_arm["Armor"].split('/')[1]), #Only max value is stored
        int(r_arm["Success"].split('/')[1]),
        int(r_arm["Power"].split('/')[1]),
        int(r_arm["Heating"].split('/')[1]),
        int(r_arm["Cooldown"].split('/')[1]),
        r_arm["HV"],
        r_arm["Ability Type"],
        r_arm["Ability Name"],
        r_arm["Description"], #Ability field replaced with description due to some noise in HTML data
        r_arm["Rank 5"],
        meowcorp_data[r_arm["Model"]]["Gender"],
        meowcorp_data[r_arm["Model"]]["Obtained from"]
        ) for r_arm in data if r_arm["Model"] in meowcorp_data.keys()]

    cursor.executemany(
        '''INSERT INTO right_arm (
            model, 
            id, 
            name, 
            armor, 
            success, 
            power, 
            heating, 
            cooldown, 
            hv, 
            ability_type, 
            ability_name, 
            ability,
            rank_5,
            gender,
            obtained
            ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
        '''
        , tuples
    )
    connection.commit()

def insertLARMS(data):
    tuples = [
        (
        l_arm["Model"], 
        l_arm["ID"],
        l_arm["Name"],
        int(l_arm["Armor"].split('/')[1]), #Only max value is stored
        int(l_arm["Success"].split('/')[1]),
        int(l_arm["Power"].split('/')[1]),
        int(l_arm["Heating"].split('/')[1]),
        int(l_arm["Cooldown"].split('/')[1]),
        l_arm["HV"],
        l_arm["Ability Type"],
        l_arm["Ability Name"],
        l_arm["Ability"],
        l_arm["Rank 5"],
        meowcorp_data[l_arm["Model"]]["Gender"],
        meowcorp_data[l_arm["Model"]]["Obtained from"]
        ) for l_arm in data if l_arm["Model"] in meowcorp_data.keys()]

    cursor.executemany(
        '''INSERT INTO left_arm (
            model, 
            id, 
            name, 
            armor, 
            success, 
            power, 
            heating, 
            cooldown, 
            hv, 
            ability_type, 
            ability_name, 
            ability, 
            rank_5,
            gender,
            obtained
            ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
        '''
        , tuples
    )
    connection.commit()

def insertLEGS(data):
    tuples = [
        (
        leg["Model"], 
        leg["ID"],
        leg["Name"],
        leg["Type"],
        int(leg["Armor"].split('/')[1]), #Only max value is stored
        int(leg["Anti-melee"].split('/')[1]),
        int(leg["Anti-status"].split('/')[1]),
        int(leg["Anti-ranged"].split('/')[1]),
        int(leg["Base Speed"].split('/')[1]),
        leg["HV"],
        leg["Ability Name"],
        leg["Ability"],
        leg["Rank 5"],
        meowcorp_data[leg["Model"]]["Gender"],
        meowcorp_data[leg["Model"]]["Obtained from"]
        ) for leg in data if leg["Model"] in meowcorp_data.keys()]

    cursor.executemany(
        '''INSERT INTO leg (
            model,
            id,
            name,
            type,
            armor,
            anti_melee,
            anti_status,
            anti_ranged,
            base_speed,
            hv,
            ability_name,
            ability,
            rank_5,
            gender,
            obtained
            ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
        '''
        , tuples
    )
    connection.commit()

def insertHEADS(data):
    tuples = [
        (
        head["Model"], 
        head["ID"],
        head["Name"],
        int(head["Armor"].split('/')[1]),
        int(head["Success"].split('/')[1]),
        int(head["Power"].split('/')[1]),
        int(head["Heating"].split('/')[1]),
        int(head["Cooldown"].split('/')[1]),
        head["Ammo"],
        head["HV"],
        head["Ability Type"],
        head["Ability Name"],
        head["Ability"],
        head["Rank 5"],
        meowcorp_data[head["Model"]]["Gender"],
        meowcorp_data[head["Model"]]["Obtained from"]
        ) for head in data if head["Model"] in meowcorp_data.keys()]

    cursor.executemany(
        '''INSERT INTO head (
            model,
            id,
            name,
            armor,
            success,
            power,
            heating,
            cooldown,
            ammo,
            hv,
            ability_type,
            ability_name,
            ability,
            rank_5,
            gender,
            obtained
            ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
        '''
        , tuples
    )
    connection.commit()



def cleanData():
    clean_h = []
    clean_la = []
    clean_ra = []
    clean_l = []
    print("Cleaning npc data...")
    print("Original count: " + str(len(heads_data)))
    for h in heads_data:
        if h["Model"] not in npc_keys:
            clean_h.append(h)
    for la in l_arms_data:
        if la["Model"] not in npc_keys:
            clean_la.append(la)
    for ra in r_arms_data:
        if ra["Model"] not in npc_keys:
            clean_ra.append(ra)
    for l in legs_data:
        if l["Model"] not in npc_keys:
            clean_l.append(l)
    print("Count after cleaning: " + str(len(clean_h)))
    return [clean_h, clean_la, clean_ra, clean_l]

def fillDB():
    cleandata = cleanData()

    heads_data = cleandata[0]
    l_arms_data = cleandata[1]
    r_arms_data = cleandata[2]
    legs_data = cleandata[3]

    insertHEADS(heads_data)
    insertLARMS(l_arms_data)
    insertRARMS(r_arms_data)
    insertLEGS(legs_data)

createDB()
fillDB()

connection.close()
