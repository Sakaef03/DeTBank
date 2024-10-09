import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class CrudBD {
    public void insertAccount(User uD) {
        String sqlInsert = "INSERT INTO ACCOUNT(AGENCY,NAME,BALANCE) VALUES (?,?,?)";
        Connection conn = ConnFactory.getConn();
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sqlInsert);
            stmt.setInt(1, uD.getAgency());
            stmt.setString(2, uD.getName());
            stmt.setDouble(3, uD.getBalance());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void updateAccount(User uD){
        String   sqlUpdate = "UPDATE ACCOUNT SET BALANCE = ? WHERE AGENCY = ?";
        Connection  conn = ConnFactory.getConn();
        PreparedStatement stmt = null;
        try
        {   stmt = conn.prepareStatement(sqlUpdate);
            stmt.setDouble(1, uD.getBalance());
            stmt.setInt(2, uD.getAgency());
            stmt.executeUpdate();
        }
        catch(SQLException e)
        {   try
            {   conn.rollback();
            }
            catch(SQLException ex)
            {   System.out.println("Erro ao atualizar o saldo" + ex.toString());
            }
        }
        finally
        {   ConnFactory.closeConn(conn, stmt);
        }       
    }

    public void showBalance(User uD){
       
        String   sqlSelect = "SELECT BALANCE FROM ACCOUNT WHERE AGENCY = ?";
        Connection  conn = ConnFactory.getConn();
        PreparedStatement stmt = null;
        ResultSet rs;
        try
        {   stmt = conn.prepareStatement(sqlSelect);
            stmt.setInt(1, uD.getAgency());
            rs = stmt.executeQuery();
            if(rs.next())
            {   
               uD.setBalance(rs.getDouble(1));
            }
        }
        catch(SQLException ex)
        {   JOptionPane.showMessageDialog(null,"Erro ao buscar o saldo" + ex.toString());
        }
        finally
        {   ConnFactory.closeConn(conn, stmt);
        }   
    }

    public void printBillOfSale(User uD){
        String   sqlSelect = "SELECT AGENCY,NAME,BALANCE FROM ACCOUNT WHERE AGENCY = ?";
        Connection  conn = ConnFactory.getConn();
        PreparedStatement stmt = null;
        ResultSet rs;
        try
        {   stmt = conn.prepareStatement(sqlSelect);
            stmt.setInt(1, uD.getAgency());
            rs = stmt.executeQuery();
            if(rs.next())
            {  
               uD.setAgency(rs.getInt(1));  
               uD.setName(rs.getString(2));
               uD.setBalance(rs.getDouble(3));
            }
        }
        catch(SQLException ex)
        {   JOptionPane.showMessageDialog(null,"Erro ao buscar os dados da conta" + ex.toString());
        }
        finally
        {   ConnFactory.closeConn(conn, stmt);
        }   
    }

}