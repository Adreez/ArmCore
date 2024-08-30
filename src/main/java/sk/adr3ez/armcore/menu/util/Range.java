package sk.adr3ez.armcore.menu.util;

public record Range(Integer min, Integer max) {

    public Integer[] getValues() {

        Integer[] values = new Integer[max - min + 1];
		int index = 0;
        for (int i = min; i <= max; i++) {
            values[index] = i;
			index++;
        }

        return values;
    }

}
