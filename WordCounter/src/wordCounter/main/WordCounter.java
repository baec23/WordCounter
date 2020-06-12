package wordCounter.main;

import java.util.*;

import javax.swing.JProgressBar;

import java.io.*;

/*
 * *PROBLEMS
 * 
 * Parsing: Quotation marks
 * 			Hyphens
 * 			Numbers
 */
public class WordCounter
{
	HashMap<String, Integer> wordCounts;
	TreeMap<Integer, ArrayList<String>> sortedWords;
	HashSet<String> articles;
	HashSet<String> conjunctions;
	HashSet<String> pronouns;
	HashSet<String> prepositions;
	HashSet<String> customFilters;
	double numArticles;
	double numConjunctions;
	double numPronouns;
	double numPrepositions;
	double numCustomFilters;
	boolean finished;
	boolean filterArticles;
	boolean filterConjunctions;
	boolean filterPronouns;
	boolean filterPrepositions;
	boolean filterCustoms;
	double totalWordCount;
	int totalUniqueWordCount;

	public WordCounter()
	{
		wordCounts = new HashMap<String, Integer>();
		sortedWords = new TreeMap<Integer, ArrayList<String>>(Collections.reverseOrder());
		articles = new HashSet<String>();
		conjunctions = new HashSet<String>();
		pronouns = new HashSet<String>();
		prepositions = new HashSet<String>();
		customFilters = new HashSet<String>();
		finished = false;
		filterArticles = false;
		filterConjunctions = false;
		filterPronouns = false;
		filterPrepositions = false;
		filterCustoms = false;
		numArticles = 0;
		numConjunctions = 0;
		numPronouns = 0;
		numPrepositions = 0;
		numCustomFilters = 0;
		totalUniqueWordCount = 0;
	}

	public void initialize(String input, JProgressBar jb)
	{
		String[] splitString = input.split("\\s+");
		jb.setValue(20);
		totalWordCount = splitString.length;
		for (String currWord : splitString)
		{
			loadArticles();
			loadPrepositions();
			loadConjunctions();
			loadPronouns();
			currWord = trimGarbage(currWord);
			if (!wordCounts.containsKey(currWord))
				wordCounts.put(currWord, 1);
			else
				wordCounts.put(currWord, wordCounts.get(currWord) + 1);
		}
		totalUniqueWordCount = wordCounts.keySet().size();
		jb.setValue(50);

		// sort words
		Iterator<String> it = wordCounts.keySet().iterator();
		while (it.hasNext())
		{
			String currWord = it.next();
			int currCount = wordCounts.get(currWord);

			if (!sortedWords.containsKey(currCount))
			{
				ArrayList<String> words = new ArrayList<String>();
				words.add(currWord);
				sortedWords.put(currCount, words);
			} else
			{
				ArrayList<String> words = sortedWords.get(currCount);
				words.add(currWord);
				sortedWords.put(currCount, words);
			}
		}
		jb.setValue(95);
	}

	private String trimGarbage(String s)
	{
		return s.replaceAll("\\p{Punct}", "").toLowerCase();
	}

	private void loadArticles()
	{
		try
		{
			Scanner sc = new Scanner(new FileReader("resources/englishArticles.txt"));
			while (sc.hasNext())
			{
				articles.add(trimGarbage(sc.next()));
			}
			sc.close();
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void loadConjunctions()
	{
		try
		{
			Scanner sc = new Scanner(new FileReader("resources/englishConjunctions.txt"));
			while (sc.hasNext())
			{
				conjunctions.add(trimGarbage(sc.next()));
			}
			sc.close();
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void loadPrepositions()
	{
		try
		{
			Scanner sc = new Scanner(new FileReader("resources/englishPrepositions.txt"));
			while (sc.hasNext())
			{
				prepositions.add(trimGarbage(sc.next()));
			}
			sc.close();
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void loadPronouns()
	{
		try
		{
			Scanner sc = new Scanner(new FileReader("resources/englishPronouns.txt"));
			while (sc.hasNext())
			{
				pronouns.add(trimGarbage(sc.next()));
			}
			sc.close();
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getCommonWords(int numWords)
	{
		if (numWords == -1)
			numWords = (int) totalUniqueWordCount;
		Iterator<Integer> it = sortedWords.keySet().iterator();
		StringBuilder sb = new StringBuilder();
		int filteredCount = 0;
		while (it.hasNext() && numWords > 0)
		{
			int currKey = it.next();
			ArrayList<String> words = sortedWords.get(currKey);
			boolean printed = false;
			for (int i = 0; i < words.size(); i++)
			{
				printed = shouldPrint(words.get(i));
				if (printed)
				{
					sb.append(words.get(i) + " | " + currKey + " | "
							+ String.format("%.3f", currKey / totalWordCount * 100) + "% \n");
					numWords--;
				} else
					filteredCount++;
			}
		}
		sb.append("\n\n-----------------\n\n");
		sb.append("Total Unique Word Count = " + totalUniqueWordCount + "\n");
		sb.append("Total Word Count = " + totalWordCount + "\n");
		sb.append("Total Word Count (Filtered) = " + (totalUniqueWordCount - filteredCount));
		return sb.toString();
	}

	private boolean shouldPrint(String s)
	{
		boolean print = true;
		if (filterArticles && articles.contains(s))
		{
			numArticles++;
			print = false;
		}
		if (filterPronouns && pronouns.contains(s))
		{
			numPronouns++;
			print = false;
		}
		if (filterPrepositions && prepositions.contains(s))
		{
			numPrepositions++;
			print = false;
		}
		if (filterConjunctions && conjunctions.contains(s))
		{
			numConjunctions++;
			print = false;
		}
		if (filterCustoms && customFilters.contains(s))
		{
			numCustomFilters++;
			print = false;
		}
		return print;
	}

	public void setCustomFilters(String in)
	{
		for (String currWord : in.split("\\s+"))
		{
			customFilters.add(currWord);
			System.out.println(currWord + " added to custom filters");
		}

	}

	public void setFilterArticles(boolean opt)
	{
		filterArticles = opt;
	}

	public void setFilterConjunctions(boolean opt)
	{
		filterConjunctions = opt;
	}

	public void setFilterPronouns(boolean opt)
	{
		filterPronouns = opt;
	}

	public void setFilterPrepositions(boolean opt)
	{
		filterPrepositions = opt;
	}

	public void setFilterCustoms(boolean opt)
	{
		filterCustoms = opt;
	}

}
