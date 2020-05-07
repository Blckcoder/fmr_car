package com.fmrnz.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by eurysinfosystems on 19/06/18.
 */

public class FilterModel {

    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> vehicleTypeList = new ArrayList<String>();
        vehicleTypeList.add("Sedan");
        vehicleTypeList.add("Hatchback");
        vehicleTypeList.add("SUV");
        vehicleTypeList.add("Micro Car");
        vehicleTypeList.add("Saloon");
        vehicleTypeList.add("Estate");
        vehicleTypeList.add("MPV");
        vehicleTypeList.add("Coupe");
        vehicleTypeList.add("Convertible");
        vehicleTypeList.add("Wagon");
        vehicleTypeList.add("UTE");
        vehicleTypeList.add("Van");
        vehicleTypeList.add("Crossover");
        vehicleTypeList.add("Sports");
        vehicleTypeList.add("Luxury");
        vehicleTypeList.add("Pickup Truck");
        vehicleTypeList.add("All");
        vehicleTypeList.add("Others");

        List<String> vehicleMakeList = new ArrayList<String>();
        vehicleMakeList.add("Hyundai");
        vehicleMakeList.add("Toyota");
        vehicleMakeList.add("Mahindra");
        vehicleMakeList.add("Nisaan");
        vehicleMakeList.add("Mazda");
        vehicleMakeList.add("Subaru");
        vehicleMakeList.add("Suzuki");
        vehicleMakeList.add("Isuzu");
        vehicleMakeList.add("Mitsubishi");
        vehicleMakeList.add("Lexus");
        vehicleMakeList.add("Kia");
        vehicleMakeList.add("Mini");
        vehicleMakeList.add("All");
        vehicleMakeList.add("Others");

        List<String> transmissionList = new ArrayList<String>();
        transmissionList.add("Automatic Transmission (AT)");
        transmissionList.add("Manual Transmission (MT)");
        transmissionList.add("Automated Manual Transmision (AMT)");
        transmissionList.add("Tiptronic Transmission");
        transmissionList.add("Direct Shift Gearbox (DSG)");
        transmissionList.add("Dual Clutch Transmission (DCT)");
        transmissionList.add("Continuously Variable  Transmission (CVT)");
        transmissionList.add("Semi Automatic  Transmission (SMT)");
        transmissionList.add("All");
        transmissionList.add("Others");

        List<String> fuelTypeList = new ArrayList<String>();
        fuelTypeList.add("Diesel");
        fuelTypeList.add("Petrol");
        fuelTypeList.add("Electic");
        fuelTypeList.add("Hybrid");
        fuelTypeList.add("Plug-In-Hybrid");
        fuelTypeList.add("CNG");
        fuelTypeList.add("LPG");
        fuelTypeList.add("Solar");
        fuelTypeList.add("Ethanol");
        fuelTypeList.add("Methanol");
        fuelTypeList.add("Propane");
        fuelTypeList.add("Biodiesel");
        fuelTypeList.add("Hydrogen Fuel Cell");
        fuelTypeList.add("All");
        fuelTypeList.add("Others");

        List<String> ownerTypeList = new ArrayList<String>();
        ownerTypeList.add("Agency");
        ownerTypeList.add("Male");
        ownerTypeList.add("Female");
        ownerTypeList.add("All");
        ownerTypeList.add("Others");

        List<String> seatList = new ArrayList<String>();
        seatList.add("2");
        seatList.add("3");
        seatList.add("4");
        seatList.add("5");
        seatList.add("6");
        seatList.add("7");
        seatList.add("8");
        seatList.add("9");
        seatList.add("10");
        seatList.add("More");
        seatList.add("All");

        List<String> makeYearList = new ArrayList<String>();
        makeYearList.add("1968");
        makeYearList.add("2018");

        List<String> priceList = new ArrayList<String>();
        priceList.add("500");
        priceList.add("10000");

        List<String> distanceList = new ArrayList<String>();
        distanceList.add("2");
        distanceList.add("50");

        expandableListDetail.put("Vehicle Type", vehicleTypeList);
        expandableListDetail.put("Vehicle Make", vehicleMakeList);
        expandableListDetail.put("Transmission", transmissionList);
        expandableListDetail.put("Fuel Type", fuelTypeList);
        expandableListDetail.put("Owner Type", ownerTypeList);
        expandableListDetail.put("Number Of Seats", seatList);
        expandableListDetail.put("Make Year",makeYearList);
//        expandableListDetail.put("Search By Price",priceList);
//        expandableListDetail.put("Search By Distance",distanceList);



        return expandableListDetail;
    }
}
