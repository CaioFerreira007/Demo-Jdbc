package application;

import db.DB;
import db.DbException;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement pt = null;

		try{
			conn = DB.getConnection();
			pt = conn.prepareStatement(
					"INSERT INTO seller"
							+ " (Name,Email,BirthDate,BaseSalary,DepartmentId)"
							+ "VALUES "
							+ "(?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			pt.setString(1,"Caio Gustavo");
			pt.setString(2, "caio@gmail.com");
			pt.setDate(3, new java.sql.Date(sdf.parse("10/07/2002").getTime()));
			pt.setDouble(4, 4500.00);
			pt.setInt(5, 1);
			
	// pt = conn.prepareStatement("insert into department (Name) values ('d1'), ('d2')", Statement.RETURN_GENERATED_KEYS);


			int rowsAffected = pt.executeUpdate();
			if(rowsAffected>0){

				ResultSet rs1= pt.getGeneratedKeys();
				while(rs1.next()){
					int id = rs1.getInt(1);
					System.out.println("ID: "+id);
				}

			}else{
				System.out.println("No rows affected!");
			}


		}catch(SQLException e){
			throw new DbException(e.getMessage());
		}catch (ParseException e) {
           throw new RuntimeException(e);
       }
		finally {
			DB.closeStatement(pt);

		}

        try {
			conn = DB.getConnection();
			st = conn.createStatement();
		rs = st.executeQuery("select * from seller");
		
		while(rs.next()) {
			System.out.println(rs.getInt("Id") + ", " + rs.getString("Name"));
		}
			
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}




}
