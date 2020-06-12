package wordCounter.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.SpringLayout;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;

import wordCounter.main.WordCounter;
import javax.swing.JProgressBar;

public class WordCounterWindow
{

	private JFrame frmWordCounter;
	private JButton analyze_button;
	private JTextArea results_textArea;
	private JTextArea sourceText_textArea;
	private JTextField numWordsToDisplay_textField;
	private WordCounter wc;
	private JTabbedPane tabbedPane;
	private JPanel panel;
	private JCheckBox articlesFilter_checkBox;
	private JCheckBox pronounsFilter_checkBox;
	private JCheckBox conjunctionsFilter_checkBox;
	private JCheckBox prepositionsFilter_checkBox;
	private JButton applyFilters_button;
	private JCheckBox customFilter_checkBox;
	private JPanel filters_TabPanel;
	private JButton loadCustomFilters_button;
	private JScrollPane scrollPane;
	private JTextArea customFilters_textArea;
	private JProgressBar progressBar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					WordCounterWindow window = new WordCounterWindow();
					window.frmWordCounter.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WordCounterWindow()
	{
		initialize();
		createEvents();
		wc = new WordCounter();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{

		frmWordCounter = new JFrame();
		frmWordCounter.setIconImage(Toolkit.getDefaultToolkit()
				.getImage(WordCounterWindow.class.getResource("/wordCounter/resources/abcIcon_32.png")));
		frmWordCounter.setTitle("Word Counter");
		frmWordCounter.setBackground(Color.LIGHT_GRAY);
		frmWordCounter.setBounds(100, 100, 850, 610);
		frmWordCounter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmWordCounter.getContentPane().setLayout(new CardLayout(0, 0));

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmWordCounter.getContentPane().add(tabbedPane, "name_2343433818700");

		JPanel input_TabPanel = new JPanel();
		tabbedPane.addTab("Input", null, input_TabPanel, null);

		analyze_button = new JButton("Analyze");

		JLabel sourceText_label = new JLabel("Source Text");

		JLabel numWords_label = new JLabel("Number of words to display");

		numWordsToDisplay_textField = new JTextField();
		numWordsToDisplay_textField.setText("50");
		numWordsToDisplay_textField.setColumns(10);

		JScrollPane sourceText_scrollPane = new JScrollPane();
		
		progressBar = new JProgressBar();
		GroupLayout gl_input_TabPanel = new GroupLayout(input_TabPanel);
		gl_input_TabPanel.setHorizontalGroup(
			gl_input_TabPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_input_TabPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_input_TabPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 809, Short.MAX_VALUE)
						.addComponent(sourceText_label, Alignment.TRAILING)
						.addGroup(Alignment.TRAILING, gl_input_TabPanel.createSequentialGroup()
							.addComponent(sourceText_scrollPane, GroupLayout.DEFAULT_SIZE, 806, Short.MAX_VALUE)
							.addGap(3))
						.addComponent(analyze_button, Alignment.TRAILING)
						.addGroup(Alignment.TRAILING, gl_input_TabPanel.createParallelGroup(Alignment.LEADING, false)
							.addComponent(numWordsToDisplay_textField)
							.addComponent(numWords_label)))
					.addContainerGap())
		);
		gl_input_TabPanel.setVerticalGroup(
			gl_input_TabPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_input_TabPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(sourceText_label)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(sourceText_scrollPane, GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(numWords_label)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(numWordsToDisplay_textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(analyze_button)
					.addGap(33)
					.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);

		sourceText_textArea = new JTextArea();
		sourceText_scrollPane.setViewportView(sourceText_textArea);
		input_TabPanel.setLayout(gl_input_TabPanel);

		JPanel output_TabPanel = new JPanel();
		tabbedPane.addTab("Output", null, output_TabPanel, null);

		JScrollPane results_scrollPane = new JScrollPane();
		GroupLayout gl_output_TabPanel = new GroupLayout(output_TabPanel);
		gl_output_TabPanel.setHorizontalGroup(gl_output_TabPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_output_TabPanel.createSequentialGroup().addContainerGap()
						.addComponent(results_scrollPane, GroupLayout.DEFAULT_SIZE, 804, Short.MAX_VALUE).addGap(15)));
		gl_output_TabPanel.setVerticalGroup(gl_output_TabPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_output_TabPanel.createSequentialGroup().addContainerGap()
						.addComponent(results_scrollPane, GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE).addGap(14)));

		results_textArea = new JTextArea();
		results_textArea.setEditable(false);
		results_scrollPane.setViewportView(results_textArea);

		panel = new JPanel();
		results_scrollPane.setColumnHeaderView(panel);

		articlesFilter_checkBox = new JCheckBox("Hide Articles");
		panel.add(articlesFilter_checkBox);

		conjunctionsFilter_checkBox = new JCheckBox("Hide Conjunctions");
		panel.add(conjunctionsFilter_checkBox);

		prepositionsFilter_checkBox = new JCheckBox("Hide Prepositions");
		panel.add(prepositionsFilter_checkBox);

		pronounsFilter_checkBox = new JCheckBox("Hide Pronouns");
		panel.add(pronounsFilter_checkBox);

		customFilter_checkBox = new JCheckBox("Hide Custom Filters");
		panel.add(customFilter_checkBox);

		applyFilters_button = new JButton("Apply Filters");

		panel.add(applyFilters_button);
		output_TabPanel.setLayout(gl_output_TabPanel);

		filters_TabPanel = new JPanel();
		tabbedPane.addTab("Custom Filters", null, filters_TabPanel, null);
		
		loadCustomFilters_button = new JButton("Set Filters");
		
		scrollPane = new JScrollPane();
		
		GroupLayout gl_filters_TabPanel = new GroupLayout(filters_TabPanel);
		gl_filters_TabPanel.setHorizontalGroup(
			gl_filters_TabPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_filters_TabPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_filters_TabPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(loadCustomFilters_button)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 809, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_filters_TabPanel.setVerticalGroup(
			gl_filters_TabPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_filters_TabPanel.createSequentialGroup()
					.addGap(13)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(loadCustomFilters_button)
					.addContainerGap())
		);
		
		customFilters_textArea = new JTextArea();
		scrollPane.setViewportView(customFilters_textArea);
		filters_TabPanel.setLayout(gl_filters_TabPanel);
	}

	private void createEvents()
	{
		analyze_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String sourceText = sourceText_textArea.getText();
				wc = new WordCounter();	
				wc.initialize(sourceText, progressBar);
				String numWordsToDisplayString = numWordsToDisplay_textField.getText();
				String result = null;
				try
				{
					int numWordsToDisplay = 0;
					if(numWordsToDisplayString.toLowerCase().contentEquals("all"))
						numWordsToDisplay = -1;
					else
						numWordsToDisplay = Integer.parseInt(numWordsToDisplayString);
					result = wc.getCommonWords(numWordsToDisplay);
				} catch (NumberFormatException nfe)
				{
					JOptionPane.showMessageDialog(null, "Invalid Number of Words");
				}
				results_textArea.setText(result);
				tabbedPane.setSelectedIndex(1);
			}
		});

		applyFilters_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				wc.setFilterArticles(articlesFilter_checkBox.isSelected());
				wc.setFilterConjunctions(conjunctionsFilter_checkBox.isSelected());
				wc.setFilterPrepositions(prepositionsFilter_checkBox.isSelected());
				wc.setFilterPronouns(pronounsFilter_checkBox.isSelected());
				wc.setFilterCustoms(customFilter_checkBox.isSelected());
				String numWordsToDisplayString = numWordsToDisplay_textField.getText();
				String result = null;
				try
				{
					int numWordsToDisplay = 0;
					if(numWordsToDisplayString.toLowerCase().contentEquals("all"))
						numWordsToDisplay = -1;
					else
						numWordsToDisplay = Integer.parseInt(numWordsToDisplayString);
					result = wc.getCommonWords(numWordsToDisplay);
				} catch (NumberFormatException nfe)
				{
					JOptionPane.showMessageDialog(null, "Invalid Number of Words");
				}		
				results_textArea.setText(result);
			}
		});
		
		loadCustomFilters_button.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				wc.setCustomFilters((customFilters_textArea.getText()));
				customFilter_checkBox.setSelected(true);
				tabbedPane.setSelectedIndex(1);				
			}
		});

	}
}
