/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Service.PaymentService;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.events.DataChangedListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.validation.GroupConstraint;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;

/**
 *
 * @author Arshavin
 */
public class PayementGUI {

    private Form val;

    public PayementGUI(String amount) {
        val = new Form("Validation");
        TableLayout tl;
        int spanButton = 2;
        if (Display.getInstance().isTablet()) {
            tl = new TableLayout(7, 2);
        } else {
            tl = new TableLayout(14, 1);
            spanButton = 1;
        }
        tl.setGrowHorizontally(true);
        val.setLayout(tl);

        val.addComponent(new Label("First Name"));
        TextField firstName = new TextField();
        val.addComponent(firstName);

        val.addComponent(new Label("Surname"));
        TextField surname = new TextField();
        val.addComponent(surname);

        val.addComponent(new Label("E-Mail"));
        TextField email = new TextField();
        email.setConstraint(TextArea.EMAILADDR);
        val.addComponent(email);

        val.addComponent(new Label("Credit Card"));

        Container creditCardContainer = new Container(new GridLayout(1, 4));
        final TextField num1 = new TextField(4);
        final TextField num2 = new TextField(4);
        final TextField num3 = new TextField(4);
        final TextField num4 = new TextField(4);

        num1.setConstraint(TextArea.NUMERIC);
        num2.setConstraint(TextArea.NUMERIC);
        num3.setConstraint(TextArea.NUMERIC);
        num4.setConstraint(TextArea.NUMERIC);

        creditCardContainer.addComponent(num1);
        creditCardContainer.addComponent(num2);
        creditCardContainer.addComponent(num3);
        creditCardContainer.addComponent(num4);

        val.addComponent(creditCardContainer);
        Container c = new Container( BoxLayout.x());
        c.addComponent(new Label("Month"));
        final TextField month = new TextField(2);
        c.addComponent(new Label("Year"));
        final TextField year = new TextField(2);
        c.addAll(month,year);
        val.addComponent(c);

        Button submit = new Button("Submit");
        TableLayout.Constraint cn = tl.createConstraint();
        cn.setHorizontalSpan(spanButton);
        cn.setHorizontalAlign(Component.RIGHT);
        val.addComponent(cn, submit);

        Validator v = new Validator();
        v.addConstraint(firstName, new LengthConstraint(2)).
                addConstraint(surname, new LengthConstraint(2)).
                addConstraint(email, RegexConstraint.validEmail()).
                addConstraint(num1, new LengthConstraint(4)).
                addConstraint(num2, new LengthConstraint(4)).
                addConstraint(num3, new LengthConstraint(4)).
                addConstraint(num4, new LengthConstraint(4, "Invalid Credit card")).
                addConstraint(month, new GroupConstraint(new LengthConstraint(2, "Invalid date"))).
                addConstraint(year, new GroupConstraint(new LengthConstraint(2, "Invalid date")));

        automoveToNext(num1, num2);
        automoveToNext(num2, num3);
        automoveToNext(num3, num4);
        automoveToNext(num4, month);
        automoveToNext(month, year);

        v.addSubmitButtons(submit);
        
        submit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                PaymentService ps = new PaymentService();
                ps.payemnt(amount,month.getText()+"/"+year.getText(),num1.getText()+num2.getText()+num3.getText()+num4.getText());
                CartGUI cghui = new CartGUI();
                cghui.getForm().show();
                
            }
        });

        val.show();
    }

    private void automoveToNext(final TextField current, final TextField next) {
        current.addDataChangeListener(new DataChangedListener() {
            public void dataChanged(int type, int index) {
                if (current.getText().length() == 5) {
                    Display.getInstance().stopEditing(current);
                    String val = current.getText();
                    current.setText(val.substring(0, 4));
                    next.setText(val.substring(4));
                    Display.getInstance().editString(next, 5, current.getConstraint(), next.getText());
                }
            }
        });
    }

    public Form getVal() {
        return val;
    }

    public void setVal(Form val) {
        this.val = val;
    }

}
