import sqlite3, json

heads_file = open('heads.json', encoding="utf8")
heads_data = json.load(heads_file)

r_arms_file = open('right-arms.json', encoding="utf8")
r_arms_data = json.load(r_arms_file)

l_arms_file = open('left-arms.json', encoding="utf8")
l_arms_data = json.load(l_arms_file)

legs_file = open('legs.json', encoding="utf8")
legs_data = json.load(legs_file)

connection = sqlite3.connect('medarot-s.db')
cursor = connection.cursor()

cursor.execute('drop table right_arm')
cursor.execute('drop table left_arm')
cursor.execute('drop table leg')
cursor.execute('drop table head')

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
            rank_5 text
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
            rank_5 text
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
            rank_5 text
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
            rank_5 text
        )
        '''
    )
    connection.commit()

def insertRARMS():
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
        r_arm["Rank 5"]
        ) for r_arm in r_arms_data]

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
            rank_5
            ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)
        '''
        , tuples
    )
    connection.commit()

def insertLARMS():
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
        l_arm["Rank 5"]
        ) for l_arm in l_arms_data]

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
            rank_5
            ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)
        '''
        , tuples
    )
    connection.commit()

def insertLEGS():
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
        leg["Rank 5"]
        ) for leg in legs_data]

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
            rank_5
            ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)
        '''
        , tuples
    )
    connection.commit()

def insertHEADS():
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
        head["Rank 5"]
        ) for head in heads_data]

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
            rank_5
            ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)
        '''
        , tuples
    )
    connection.commit()

def fillDB():
    insertHEADS()
    insertLARMS()
    insertRARMS()
    insertLEGS()

createDB()
fillDB()

def query1():
    cursor.execute('''
    SELECT MAX(power), MAX(armor), MAX(success), MAX(heating), MAX(cooldown), model FROM right_arm ORDER by model
    ''')

    rows = cursor.fetchall()

    for row in rows:
        print(row)

def query2():
    cursor.execute('''
    SELECT SUM(power, armor), MAX(success), MAX(heating), MAX(cooldown), model FROM right_arm ORDER by model
    ''')

    rows = cursor.fetchall()

    for row in rows:
        print(row)

connection.close()