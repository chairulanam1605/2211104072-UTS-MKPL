public class TaxFunction {
    
    // Constants for tax calculation
    private static final double TAX_RATE = 0.05;
    private static final int BASE_NON_TAXABLE_INCOME = 54_000_000;
    private static final int MARRIED_ADDITION = 4_500_000;
    private static final int PER_CHILD_ADDITION = 1_500_000;
    private static final int MAX_CHILDREN = 3;
    private static final int MIN_TAX = 0;
    private static final int MONTHS_IN_YEAR = 12;

    public static int calculateTax(int monthlySalary, int otherMonthlyIncome, int numberOfMonthWorking, int deductible, boolean isMarried, int numberOfChildren) {
        
        validateInput(numberOfMonthWorking, numberOfChildren);
        
        int taxableIncome = calculateTaxableIncome(
            monthlySalary, 
            otherMonthlyIncome, 
            numberOfMonthWorking, 
            deductible,
            isMarried, 
            numberOfChildren
        );
        
        return calculateFinalTax(taxableIncome);
    }
    
    private static void validateInput(int numberOfMonthWorking, int numberOfChildren) {
        if (numberOfMonthWorking < 0) {
            throw new IllegalArgumentException("Number of working months cannot be negative");
        }
        
        if (numberOfMonthWorking > MONTHS_IN_YEAR) {
            throw new IllegalArgumentException("Cannot work more than " + MONTHS_IN_YEAR + " months in a year");
        }
        
        if (numberOfChildren < 0) {
            throw new IllegalArgumentException("Number of children cannot be negative");
        }
    }
    
    /**
     * Calculates taxable income after deductions
     */
    private static int calculateTaxableIncome(int monthlySalary, int otherMonthlyIncome, int numberOfMonthWorking, int deductible, boolean isMarried, int numberOfChildren) {
        int totalIncome = (monthlySalary + otherMonthlyIncome) * numberOfMonthWorking;
        int nonTaxableIncome = calculateNonTaxableIncome(isMarried, numberOfChildren);
        return totalIncome - deductible - nonTaxableIncome;
    }
    
    /**
     * Calculates non-taxable income based on marital status and number of children
     */
    private static int calculateNonTaxableIncome(boolean isMarried, int numberOfChildren) {
        int childrenCount = Math.min(numberOfChildren, MAX_CHILDREN);
        int childrenAddition = childrenCount * PER_CHILD_ADDITION;
        
        return BASE_NON_TAXABLE_INCOME + (isMarried ? MARRIED_ADDITION : 0) + childrenAddition;
    }
    
    /**
     * Calculates final tax amount with minimum 0
     */
    private static int calculateFinalTax(int taxableIncome) {
        double taxAmount = taxableIncome * TAX_RATE;
        int roundedTax = (int) Math.round(taxAmount);
        return Math.max(roundedTax, MIN_TAX);
    }
}