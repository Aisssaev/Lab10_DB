package com.university.lab10_db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdvertisingRequestDAOMySQL implements IAdvertistingRequestDAO {
    private Connection con;
    private List<AdvertisingRequest> requestList = new ArrayList<>();
    private List<AdvertisingRequest> modifiedRequests = new ArrayList<>();
    private List<AdvertisingRequest> newRequests = new ArrayList<>();
    private List<AdvertisingRequest> deletedRequests = new ArrayList<>();

    public AdvertisingRequestDAOMySQL(Connection con) {
        this.con = con;
    }

    @Override
    public void add(AdvertisingRequest advertistingRequest) {
        requestList.add(advertistingRequest);
//        var sql = "insert into advertising_requests (id, request_type, client_name, placement_location, release_date, content, cost, payment_status, customer_id, publication_id)\n" +
//                "value (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//        try {
//            var stm = con.prepareStatement(sql);
//            stm.setInt(1,advertistingRequest.id);
//            stm.setString(2, advertistingRequest.type.getDescription());
//            stm.setString(3, advertistingRequest.clientName);
//            stm.setString(4, advertistingRequest.placementLocation);
//            stm.setDate(4, advertistingRequest.date);
//            stm.setString(5, advertistingRequest.content);
//            stm.setDouble(6, advertistingRequest.cost);
//            stm.setString(7, advertistingRequest.paymentStatus ? "оплачено" : "не оплачено");
//            stm.setInt(8, advertistingRequest.customerId);
//            stm.setInt(9, advertistingRequest.publicationId);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public AdvertisingRequest getAdvertistingRequest(int id) {
        return requestList.get(id);
//        var sql = "select * from advertising_requests where id = ?";
//        try {
//            var stm = con.prepareStatement(sql);
//            var advertistingRequest = new AdvertistingRequest();
//            stm.setInt(1, id);
//            var result = stm.executeQuery();
//            if (result.next()) {
//                advertistingRequest.id = id;
//                advertistingRequest.type = (Objects.equals(result.getString("request_type"), "текстова реклама")) ? RequestType.TEXT : RequestType.GRAPHIC;
//                advertistingRequest.clientName = result.getString("client_name");
//                advertistingRequest.placementLocation = result.getString("placement_location");
//                advertistingRequest.date = result.getDate("release_date");
//                advertistingRequest.content = result.getString("content");
//                advertistingRequest.cost = result.getDouble("cost");
//                advertistingRequest.paymentStatus = Objects.equals(result.getString("payment_status"), "оплачено");
//                advertistingRequest.customerId = result.getInt("customer_id");
//                advertistingRequest.publicationId = result.getInt("publication_id");
//            }
//
//            return advertistingRequest;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public List<AdvertisingRequest> getAllAdvertistingRequest() throws SQLException {
        return requestList;
    }

    @Override
    public boolean update(AdvertisingRequest advertistingRequest){


//        var sql = "update advertising_requests set request_type = ?, client_name = ?, placement_location = ?, release_date = ?, content = ?, cost = ?, payment_status = ?, customer_id = ?, publication_id = ? where id = ?";
//        try {
//            var stm = con.prepareStatement(sql);
//            stm.setString(1, advertistingRequest.type.getDescription());
//            stm.setString(2, advertistingRequest.clientName);
//            stm.setString(3, advertistingRequest.placementLocation);
//            stm.setDate(4, advertistingRequest.date);
//            stm.setString(5, advertistingRequest.content);
//            stm.setDouble(6, advertistingRequest.cost);
//            stm.setBoolean(7, advertistingRequest.paymentStatus);
//            stm.setInt(8, advertistingRequest.customerId);
//            stm.setInt(9, advertistingRequest.publicationId);
//            stm.setInt(10, advertistingRequest.id);
//
//            var rowsUpdated = stm.executeUpdate();
//            return rowsUpdated > 0;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public boolean delete(AdvertisingRequest advertistingRequest) {
        var sql = "DELETE FROM advertising_requests WHERE id = ?";

        try (var stm = con.prepareStatement(sql)) {
            stm.setInt(1, advertistingRequest.id);

            var rowsDeleted = stm.executeUpdate();

            return rowsDeleted > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
