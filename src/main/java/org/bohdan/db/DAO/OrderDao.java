package org.bohdan.db.DAO;

import org.bohdan.db.DBManager;
import org.bohdan.db.Fields;
import org.bohdan.db.bean.OrderTours;
import org.bohdan.db.entity.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {

    private static final String SQL_FIND_ALL_ORDER =
            "SELECT * FROM travel_agencyDB.order";

    private static final String SQL_FIND_ENTITY_BY_ID_ORDER =
            "SELECT * FROM travel_agencyDB.order WHERE id=?";

    public static final String SQL_DELETE_ORDER_BY_ID =
            "DELETE FROM travel_agencyDB.order WHERE id = ?";

    public static final String SQL_INSERT_ORDER =
            "insert into travel_agencyDB.order (status, date_reg, tour_id, user_id) values" +
                    "(?, ?, ?, ?)";

    public static final String SQL_UPDATE_ORDER =
            "UPDATE travel_agencyDB.order SET status = ?, tour_id = ?, user_id = ? WHERE id = ?";

    public static final String SQL_UPDATE_STATUS_ORDER =
            "UPDATE travel_agencyDB.order SET status = ? WHERE id = ?";

    public static final String SQL_FIND_ALL_ORDERS_EN =
            "SELECT travel_agencyDB.order.id, tour.id as tour_id, tour.name_en as name, type_tour.name_en as type, country.name_en as country, tour.price, \n" +
                    "tour.count_people, tour.mark_hotel, tour.start_date, tour.days, tour.discount, travel_agencyDB.order.status, user.login\n" +
                    "FROM user, tour, travel_agencyDB.order, type_tour, country\n" +
                    "WHERE type_tour.id = tour.type_tour_id and country.id = tour.country_id \n" +
                    "and tour.id = travel_agencyDB.order.tour_id \n" +
                    "and user.id = travel_agencyDB.order.user_id\n" +
                    "order by travel_agencyDB.order.date_reg DESC";

    public static final String SQL_FIND_ALL_ORDERS_RU =
            "SELECT travel_agencyDB.order.id, tour.id as tour_id, tour.name_ru as name, type_tour.name_ru as type, country.name_ru as country, tour.price, \n" +
                    "tour.count_people, tour.mark_hotel, tour.start_date, tour.days, tour.discount, travel_agencyDB.order.status, user.login\n" +
                    "FROM user, tour, travel_agencyDB.order, type_tour, country\n" +
                    "WHERE type_tour.id = tour.type_tour_id and country.id = tour.country_id \n" +
                    "and tour.id = travel_agencyDB.order.tour_id\n" +
                    "and user.id = travel_agencyDB.order.user_id\n" +
                    "order by travel_agencyDB.order.date_reg DESC";

    public static final String SQL_FIND_ALL_ORDERS_USER_EN =
            "SELECT travel_agencyDB.order.id, tour.id as tour_id, tour.name_en as name, type_tour.name_en as type, country.name_en as country, tour.price, \n" +
                    "tour.count_people, tour.mark_hotel, tour.start_date, tour.days, tour.discount, travel_agencyDB.order.status, user.login\n" +
                    "FROM user, tour, travel_agencyDB.order, type_tour, country\n" +
                    "WHERE type_tour.id = tour.type_tour_id and country.id = tour.country_id \n" +
                    "and tour.id = travel_agencyDB.order.tour_id \n" +
                    "and user.id = travel_agencyDB.order.user_id\n" +
                    "and user.id = ?\n" +
                    "order by travel_agencyDB.order.date_reg DESC";

    public static final String SQL_FIND_ALL_ORDERS_USER_RU =
            "SELECT travel_agencyDB.order.id, tour.id as tour_id, tour.name_ru as name, type_tour.name_ru as type, country.name_ru as country, tour.price, \n" +
                    "tour.count_people, tour.mark_hotel, tour.start_date, tour.days, tour.discount, travel_agencyDB.order.status, user.login\n" +
                    "FROM user, tour, travel_agencyDB.order, type_tour, country\n" +
                    "WHERE type_tour.id = tour.type_tour_id and country.id = tour.country_id \n" +
                    "and tour.id = travel_agencyDB.order.tour_id\n" +
                    "and user.id = travel_agencyDB.order.user_id\n" +
                    "and user.id = ?\n" +
                    "order by travel_agencyDB.order.date_reg DESC";


    private Order mapOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getInt(Fields.ID));
        order.setStatus(rs.getString(Fields.STATUS));
        order.setTour_id(rs.getInt(Fields.TOUR_ID));
        order.setUser_id(rs.getInt(Fields.USER_ID));
        return order;
    }

    private OrderTours mapOrderTours(ResultSet rs) throws SQLException {
        OrderTours order = new OrderTours();
        order.setOrder_id(rs.getInt(Fields.ID));
        order.setTour_id(rs.getInt(Fields.TOUR_ID));
        order.setName(rs.getString(Fields.NAME));
        order.setType(rs.getString(Fields.TYPE));
        order.setCountry(rs.getString(Fields.COUNTRY));
        order.setPrice(rs.getFloat(Fields.PRICE));
        order.setMark_hotel(rs.getInt(Fields.MARK_HOTEL));
        order.setCount_people(rs.getInt(Fields.COUNT_PEOPLE));
        order.setStart_date(rs.getDate(Fields.START_DATE));
        order.setDays(rs.getInt(Fields.DAYS));
        order.setDiscount(rs.getFloat(Fields.DISCOUNT));
        order.setStatus(rs.getString(Fields.STATUS));
        order.setLogin(rs.getString(Fields.LOGIN));
        return order;
    }

    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();

        try (Connection con = DBManager.getInstance().getConnection();
             Statement statement = con.createStatement();
             ResultSet rs = statement.executeQuery(SQL_FIND_ALL_ORDER)) {
            while (rs.next()) {
                orders.add(mapOrder(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return orders;
    }

    public Order findEntityById(Integer id) {
        Order order = null;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(SQL_FIND_ENTITY_BY_ID_ORDER)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                order = mapOrder(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return order;
    }

    public List<OrderTours> findAllOrdersLocale(String locale) {
        if (locale.equals("EN") || locale.equals("en")){
            return findAllOrders(SQL_FIND_ALL_ORDERS_EN);
        }
        if (locale.equals("RU") || locale.equals("ru")){
            return findAllOrders(SQL_FIND_ALL_ORDERS_RU);
        }
        return null;
    }

    private List<OrderTours> findAllOrders(String sql) {
        List<OrderTours> orders = new ArrayList<>();
        try (Connection con = DBManager.getInstance().getConnection();
             Statement statement = con.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                orders.add(mapOrderTours(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return orders;
    }

    public List<OrderTours> findAllOrdersUsersLocale(String locale, Integer id) {
        if (locale.equals("EN") || locale.equals("en")){
            return findAllOrdersUsers(SQL_FIND_ALL_ORDERS_USER_EN, id);
        }
        if (locale.equals("RU") || locale.equals("ru")){
            return findAllOrdersUsers(SQL_FIND_ALL_ORDERS_USER_RU, id);
        }
        return null;
    }

    private List<OrderTours> findAllOrdersUsers(String sql, Integer id) {
        List<OrderTours> orders = new ArrayList<>();
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                orders.add(mapOrderTours(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return orders;
    }

    public boolean delete(Integer id) {
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(SQL_DELETE_ORDER_BY_ID);) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean delete(Order entity) {
        return delete(entity.getId());
    }

    public boolean create(Order entity) {
        boolean res = false;

        ResultSet rs = null;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(SQL_INSERT_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            int k = 1;
            statement.setString(k++, entity.getStatus());
            statement.setObject(k++, entity.getDate_reg());
            statement.setInt(k++, entity.getTour_id());
            statement.setInt(k++, entity.getUser_id());

            if (statement.executeUpdate() > 0) {
                rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    entity.setId(rs.getInt(1));
                    res = true;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DBManager.close(rs);
        }
        return res;
    }

    public Order update(Order entity) {
        Order order = null;

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(SQL_UPDATE_ORDER)) {
            int k = 1;
            statement.setString(k++, entity.getStatus());
            statement.setDate(k++, (Date) entity.getDate_reg());
            statement.setInt(k++, entity.getTour_id());
            statement.setInt(k++, entity.getUser_id());
            statement.setInt(k++, entity.getId());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                order = mapOrder(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return order;
    }

    public boolean updateStatus(String status, Integer id) {
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(SQL_UPDATE_STATUS_ORDER)) {
            statement.setString(1, status);
            statement.setInt(2, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
