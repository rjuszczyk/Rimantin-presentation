package pl.pharmaway.rimantin_presentation.model;

import java.io.Serializable;

public class PharmacyDataRow implements Serializable{
    public Long _id;

    public String getNazwa_apteki() {
        return nazwa_apteki;
    }

    public String getUlica() {
        return ulica;
    }

    public String getMiasto() {
        return miasto;
    }

    public String getWojewodztwo() {
        return wojewodztwo;
    }

    public String getNazwisko_przedstawiciela() {
        return nazwisko_przedstawiciela;
    }

    public String getImie_przedstawiciela() {
        return imie_przedstawiciela;
    }

    public String getRks_nazwisko() {
        return rks_nazwisko;
    }

    public String getRks_imie() {
        return rks_imie;
    }


    public String nazwa_apteki;

    public String ulica;

    public String miasto;

    public String wojewodztwo;

    public String nazwisko_przedstawiciela;

    public String imie_przedstawiciela;

    public String rks_nazwisko;

    public String rks_imie;

}
