package util;

public class Variables {

	public static String[] head_columnNames = {
            "Model",
            "Name",
            "Armor",
            "Success",
            "Power",
            "Heating",
            "Cooldown",
            "Ammo",
            "HV",
            "Gender",
            "Ability Type",
            "Ability Name",
            "Ability Text",
            "Rank 5",
            "Obtained"
            };
	
	public static String[] arms_columnNames = {
            "Model",
            "Name",
            "Armor",
            "Success",
            "Power",
            "Heating",
            "Cooldown",
            "HV",
            "Gender",
            "Ability Type",
            "Ability Name",
            "Ability Text",
            "Rank 5",
            "Obtained"
            };
	
	public static String[] legs_columnNames = {
            "Model",
            "Name",
            "Armor",
            "Anti Melee",
            "Anti Status",
            "Anti Ranged",
            "Base Speed",
            "HV",
            "Gender",
            "Type",
            "Ability Name",
            "Ability Text",
            "Rank 5",
            "Obtained"
            };
	
	public static String[] legTypes = {
			"Biped",
			"Flight",
			"Hover",
			"Multiped",
			"Sea",
			"Tank",
			"Vehicle"
	};
	
	public static String[] abilityTypes = {
			"Shooting",
			"Melee",
			"Support",
			"Special",
			"Healing",
			"Defense",
			"Plant/Trap"
	};
	
	public static int[] headColumnSize = {
			75,106,75,75,75,75,75,52,38,52,75,140,584,487,150
	};
	
	public static int[] armsColumnSize = {
			75,106,75,75,75,75,52,38,52,75,140,584,487,150
	};
	
	public static int[] legsColumnSize = {
			75,106,75,75,75,90,90,38,52,75,140,584,487,150
	};
	
	public static String[] obtainedSource = {
			"PU Gacha",
			"Crossover",
			"Gacha",
			"Fierce Robattle",
			"Event",
			"FP Gacha",
			"ML Gacha",
			"Special",
			"Start of game"
	};
}
