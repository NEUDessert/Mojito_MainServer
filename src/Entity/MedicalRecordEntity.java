package Entity;

import javax.persistence.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;


/**
 * Created by killeryuan on 2016/10/22.
 */
@Entity
@Table(name = "medical_record", schema = "mojito", catalog = "")
public class MedicalRecordEntity {
    private String phonenumber;
    private String name;
    private String medicalStatus;
    private String medicalNote;
    private String drugUse;
    private String contactsName1;
    private String contactsNumber1;
    private String contactsName2;
    private String contactsNumber2;
    private double weight;
    private double stature;
    private Date brith;
    private String irritability;
    private String bloodType;

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    // http://localhost:8080/Mojito/user/finishedMR.do?name=李晨曦&medicalStatus=阳痿治疗中&medicalNote=电刺激治疗&drugUse=肾宝&contactsName1=父亲&contactsNumber1=15734003447&contactsName2=同学&contactsNumber=12345678900&weight=77&stature=172&brith=1996-03-24

    public void setStature(Double stature) {
        this.stature = stature;
    }

    public void setBrith(Date brith) {
        this.brith = brith;
    }

    @Id
    @Column(name = "phonenumber")
    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "medical_status")
    public String getMedicalStatus() {
        return medicalStatus;
    }

    public void setMedicalStatus(String medicalStatus) {
        this.medicalStatus = medicalStatus;
    }

    @Basic
    @Column(name = "medical_note")
    public String getMedicalNote() {
        return medicalNote;
    }

    public void setMedicalNote(String medicalNote) {
        this.medicalNote = medicalNote;
    }

    @Basic
    @Column(name = "drug_use")
    public String getDrugUse() {
        return drugUse;
    }

    public void setDrugUse(String drugUse) {
        this.drugUse = drugUse;
    }

    @Basic
    @Column(name = "contacts_name_1")
    public String getContactsName1() {
        return contactsName1;
    }

    public void setContactsName1(String contactsName1) {
        this.contactsName1 = contactsName1;
    }

    @Basic
    @Column(name = "contacts_number_1")
    public String getContactsNumber1() {
        return contactsNumber1;
    }

    public void setContactsNumber1(String contactsNumber1) {
        this.contactsNumber1 = contactsNumber1;
    }

    @Basic
    @Column(name = "contacts_name_2")
    public String getContactsName2() {
        return contactsName2;
    }

    public void setContactsName2(String contactsName2) {
        this.contactsName2 = contactsName2;
    }

    @Basic
    @Column(name = "contacts_number_2")
    public String getContactsNumber2() {
        return contactsNumber2;
    }

    public void setContactsNumber2(String contactsNumber2) {
        this.contactsNumber2 = contactsNumber2;
    }

    @Basic
    @Column(name = "weight")
    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Basic
    @Column(name = "stature")
    public double getStature() {
        return stature;
    }

    public void setStature(double stature) {
        this.stature = stature;
    }

    @Basic
    @Column(name = "brith")
    public Date getBrith() {
        return brith;
    }

    public void setBrith(String brith) {

        DateFormat df = DateFormat.getDateInstance();
        try {
            java.util.Date dd = df.parse(brith);
            Date d = new Date(dd.getTime());
            System.out.println(d);
            this.brith = d;
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MedicalRecordEntity that = (MedicalRecordEntity) o;

        if (Double.compare(that.weight, weight) != 0) return false;
        if (Double.compare(that.stature, stature) != 0) return false;
        if (phonenumber != null ? !phonenumber.equals(that.phonenumber) : that.phonenumber != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (medicalStatus != null ? !medicalStatus.equals(that.medicalStatus) : that.medicalStatus != null)
            return false;
        if (medicalNote != null ? !medicalNote.equals(that.medicalNote) : that.medicalNote != null) return false;
        if (drugUse != null ? !drugUse.equals(that.drugUse) : that.drugUse != null) return false;
        if (contactsName1 != null ? !contactsName1.equals(that.contactsName1) : that.contactsName1 != null)
            return false;
        if (contactsNumber1 != null ? !contactsNumber1.equals(that.contactsNumber1) : that.contactsNumber1 != null)
            return false;
        if (contactsName2 != null ? !contactsName2.equals(that.contactsName2) : that.contactsName2 != null)
            return false;
        if (contactsNumber2 != null ? !contactsNumber2.equals(that.contactsNumber2) : that.contactsNumber2 != null)
            return false;
        if (brith != null ? !brith.equals(that.brith) : that.brith != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = phonenumber != null ? phonenumber.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (medicalStatus != null ? medicalStatus.hashCode() : 0);
        result = 31 * result + (medicalNote != null ? medicalNote.hashCode() : 0);
        result = 31 * result + (drugUse != null ? drugUse.hashCode() : 0);
        result = 31 * result + (contactsName1 != null ? contactsName1.hashCode() : 0);
        result = 31 * result + (contactsNumber1 != null ? contactsNumber1.hashCode() : 0);
        result = 31 * result + (contactsName2 != null ? contactsName2.hashCode() : 0);
        result = 31 * result + (contactsNumber2 != null ? contactsNumber2.hashCode() : 0);
        temp = Double.doubleToLongBits(weight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(stature);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (brith != null ? brith.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "irritability")
    public String getIrritability() {
        return irritability;
    }

    public void setIrritability(String irritability) {
        this.irritability = irritability;
    }

    @Basic
    @Column(name = "blood_type")
    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }
}
