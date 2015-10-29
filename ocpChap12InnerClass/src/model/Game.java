package model;

import java.math.BigDecimal;

public class Game {

    private String name;

    private BigDecimal price;

    // has inner class within outer class
    private Designer designer;

    /**
     * "Regular" inner class
     */
    public class Designer {

        private String designerName;

        public void setName(final String name) {
            this.designerName = name;
        }

        public String getName() {
            return designerName;
        }

        /**
         * access to outer private class variable
         */
        public void seeGamePrice() {
            System.out.println("I am " + designerName + ", my game's price is: "
                    + price.setScale(2, BigDecimal.ROUND_UP).toString() + " euro.");
        }
    }

    public Designer getDesigner() {
        return designer;
    }

    public void setDesigner(final Designer designer) {
        this.designer = designer;
    }

    public void printMe() {
        final String color = "color";

        /**
         * Method-local inner class
         */
        class Printer {

            public void getPrinterSetting() {
                // use of outer class private variable: name
                // use of FINAL local variable
                System.out.println("I am a temp " + color + " printer for Game: " + name);
            }
        }

        // Instantiate method local class after class definition
        final Printer printer = new Printer();
        printer.getPrinterSetting();
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    /**
     * Static nested class
     */
    public static class SeparaterPrinter {

        @Override
        public String toString() {
            return "----------------------------------------------------";
        }
    }

}
