/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.mackenzie.fci.lp2.principal;

import br.mackenzie.fci.lp2.dao.CargoDAO;
import br.mackenzie.fci.lp2.dao.FuncionarioDAO;
import br.mackenzie.fci.lp2.model.Cargo;
import br.mackenzie.fci.lp2.model.Funcionario;
import java.awt.Dimension;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author RABAH
 */
public class Principal {

    public static void main(String[] args) throws Exception {
//        Class.forName("org.apache.derby.jdbc.ClientDriver");
//        Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/Empresa","mack","mack"); //edereco do servidor , nome ,a senha , 
//        Statement statement = connection.createStatement();
//        ResultSet result = statement.executeQuery("SELECT * FROM EMPRESA.CARGO");
//        while(result.next()){
//            System.out.print(result.getInt("ID_CARGO"));
//            System.out.println(" "+result.getString("NOME"));
//        }
//        
//        connection.close();
        int opcao = 10;
        do {
            opcao = menu();

            switch (opcao) {
                case 1:

                    break;

                case 2:
                    String nomeCargo = JOptionPane.showInputDialog("Informe o nome do cargo: ");

                    break;

                case 3:

                    break;

                case 4:

                    if (new FuncionarioDAO().listar().size() >= 1) {
                        String result = "Codigo                 Nome\n";
                        for (Funcionario funcionario : new FuncionarioDAO().listar()) {
                            result += funcionario.getCodigo() + "                      " + funcionario.getNome() + "\n";
                        }

                        JTextArea textArea = new JTextArea(result);
                        JScrollPane scrollPane = new JScrollPane(textArea);
//                        textArea.setLineWrap(true);
//                        textArea.setWrapStyleWord(true);
                        scrollPane.setPreferredSize(new Dimension(400, 350));

                        JOptionPane.showMessageDialog(null, scrollPane, "Lista dos funcionarios",
                                JOptionPane.DEFAULT_OPTION);
                    } else {
                        JOptionPane.showMessageDialog(null, "Não há nenhum funcionario cadastrado!");
                    }
                    break;

                case 5:
                    if (new CargoDAO().listar().size() >= 1) {
                        String result = "Codigo         Nome\n";
                        for (Cargo cargo : new CargoDAO().listar()) {
                            result += cargo.toString();
                        }
                        JTextArea textArea = new  JTextArea(result);
                        JScrollPane scrollPane = new JScrollPane(textArea);
                        scrollPane.setPreferredSize(new Dimension(350,350));
                        JOptionPane.showMessageDialog(null,scrollPane,"Lista dos cargos",JOptionPane.DEFAULT_OPTION);
                    } else {
                        JOptionPane.showMessageDialog(null, "Não há nenhum cargo cadastrado!");
                    }
                    break;
            }
        } while (opcao != 10);

    }

    public static int menu() {
        return Integer.parseInt(JOptionPane.showInputDialog("1- Adicionar um funcionario.\n2- Adicionar um cargo.\n3- Adicionar um departamento.\n4- Listar Funcionarios.\n5- Listar Cargos.\nSua escolha: "));

    }
}
