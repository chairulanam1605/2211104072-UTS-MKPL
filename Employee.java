
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;

enum Gender {
    MALE("Laki-laki"),
    FEMALE("Perempuan");

    private final String description;

    Gender(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

class SalaryCalculator {
    // Constants for salary grades
    private static final int GRADE_1_SALARY = 3_000_000;
    private static final int GRADE_2_SALARY = 5_000_000;
    private static final int GRADE_3_SALARY = 7_000_000;
    private static final double FOREIGNER_SALARY_MULTIPLIER = 1.5;

    public static int calculateSalary(int grade, boolean isForeigner) {
        int baseSalary;
        
        switch (grade) {
            case 1:
                baseSalary = GRADE_1_SALARY;
                break;
            case 2:
                baseSalary = GRADE_2_SALARY;
                break;
            case 3:
                baseSalary = GRADE_3_SALARY;
                break;
            default:
                throw new IllegalArgumentException("Invalid grade: " + grade);
        }
        
        return isForeigner ? (int)(baseSalary * FOREIGNER_SALARY_MULTIPLIER) : baseSalary;
    }
}

class EmploymentDurationCalculator {
    public static int calculateMonthsWorkedInCurrentYear(LocalDate joinDate, LocalDate currentDate) {
        if (joinDate.getYear() == currentDate.getYear()) {
            // Jika bergabung di tahun yang sama, hitung selisih bulan
            return (int)ChronoUnit.MONTHS.between(joinDate.withDayOfMonth(1), currentDate.withDayOfMonth(1)) + 1;
        } else if (joinDate.getYear() < currentDate.getYear()) {
            // Jika bergabung sebelum tahun ini, sudah bekerja 12 bulan penuh
            return 12;
        } else {
            // Jika joinDate di masa depan (tidak mungkin), return 0
            return 0;
        }
    }
}

public class Employee {

	private String employeeId;
	private String firstName;
	private String lastName;
	private String idNumber;
	private String address;
	
	private LocalDate joinDate;
	private int monthWorkingInYear;
	
	
	
	private boolean isForeigner;
    private Gender gender; 
	
	private int monthlySalary;
	private int otherMonthlyIncome;
	private int annualDeductible;
	
	private String spouseName;
	private String spouseIdNumber;

	private List<String> childNames;
	private List<String> childIdNumbers;
	
	public Employee(String employeeId, String firstName, String lastName, String idNumber, String address, int yearJoined, int monthJoined, int dayJoined, boolean isForeigner, Gender gender) {
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.idNumber = idNumber;
		this.address = address;
		this.joinDate = joinDate;
		this.isForeigner = isForeigner;
		this.gender = gender;
		
		childNames = new LinkedList<String>();
		childIdNumbers = new LinkedList<String>();
	}
	
	/**
	 * Fungsi untuk menentukan gaji bulanan pegawai berdasarkan grade kepegawaiannya (grade 1: 3.000.000 per bulan, grade 2: 5.000.000 per bulan, grade 3: 7.000.000 per bulan)
	 * Jika pegawai adalah warga negara asing gaji bulanan diperbesar sebanyak 50%
	 */
	
	public void setMonthlySalary(int grade) {    
        this.monthlySalary = SalaryCalculator.calculateSalary(grade, isForeigner);
    }
	
	public void setAnnualDeductible(int deductible) {	
		this.annualDeductible = deductible;
	}
	
	public void setAdditionalIncome(int income) {	
		this.otherMonthlyIncome = income;
	}
	
	public void setSpouse(String spouseName, String spouseIdNumber) {
		this.spouseName = spouseName;
		this.spouseIdNumber = idNumber;
	}
	
	public void addChild(String childName, String childIdNumber) {
		childNames.add(childName);
		childIdNumbers.add(childIdNumber);
	}
	
	public int getAnnualIncomeTax() {
        LocalDate currentDate = LocalDate.now();
        this.monthWorkingInYear = EmploymentDurationCalculator.calculateMonthsWorkedInCurrentYear(joinDate, currentDate);
        return TaxFunction.calculateTax(monthlySalary, otherMonthlyIncome, monthWorkingInYear, annualDeductible, spouseIdNumber.equals(""), childIdNumbers.size());
    }

	public Gender getGender() {
        return gender;
    }

	public int getMonthlySalary() {
        return monthlySalary;
    }

	public LocalDate getJoinDate() {
        return joinDate;
    }
}
