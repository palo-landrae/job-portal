package models;

public class Job {

    private int id;
    private String jobTitle;
    private String company;
    private String location;
    private String jobType;
    private int salaryMin;
    private int salaryMax;

    public Job(int id, String jobTitle, String company, String location, String jobType, int salaryMin, int salaryMax) {
        this.id = id;
        this.jobTitle = jobTitle;
        this.company = company;
        this.location = location;
        this.jobType = jobType;
        this.salaryMin = salaryMin;
        this.salaryMax = salaryMax;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public int getSalaryMin() {
        return salaryMin;
    }

    public void setSalaryMin(int salaryMin) {
        this.salaryMin = salaryMin;
    }

    public int getSalaryMax() {
        return salaryMax;
    }

    public void setSalaryMax(int salaryMax) {
        this.salaryMax = salaryMax;
    }

    public static String[] getColumnNames() {
        return new String[] {
                "id",
                "job_title",
                "company",
                "location",
                "job_type",
                "salary_min",
                "salary_max"
        };
    }

    public Object[] toArray() {
        return new Object[] {
                id,
                jobTitle,
                company,
                location,
                jobType,
                salaryMin,
                salaryMax
        };
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", jobTitle='" + jobTitle + '\'' +
                ", company='" + company + '\'' +
                ", location='" + location + '\'' +
                ", jobType='" + jobType + '\'' +
                ", salaryMin=" + salaryMin +
                ", salaryMax=" + salaryMax +
                '}';
    }
}
