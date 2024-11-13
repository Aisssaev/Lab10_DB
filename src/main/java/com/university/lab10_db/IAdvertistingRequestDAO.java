package com.university.lab10_db;

import java.sql.SQLException;
import java.util.List;

public interface IAdvertistingRequestDAO {

    public void add(AdvertisingRequest advertistingRequest) throws SQLException;

    public AdvertisingRequest getAdvertistingRequest(int id) throws SQLException;

    public List<AdvertisingRequest> getAllAdvertistingRequest() throws SQLException;

    //public ArrayList<AdvertistingRequest> findByType(RequestType type);

    public boolean update(AdvertisingRequest advertistingRequest) throws SQLException;

    public boolean delete(AdvertisingRequest advertistingRequest);
}
