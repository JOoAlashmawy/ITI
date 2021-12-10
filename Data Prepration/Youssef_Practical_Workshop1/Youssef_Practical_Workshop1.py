#!/usr/bin/env python
# coding: utf-8

# In[2]:


from selenium import webdriver
from selenium.webdriver.common.keys import Keys
import pandas as pd

driver = webdriver.Firefox()
driver.get('https://www.premierleague.com/stats/top/players/goals?se=-1')


# In[5]:


my_ranks = driver.find_elements_by_xpath('//td[@class="rank"]')
rank = []
for r in my_ranks:
    rank.append(r.text)
print(rank)


# In[8]:


#playerName
playerNames = driver.find_elements_by_xpath('//a[@class="playerName"]')
names = []
for n in playerNames:
    names.append(n.text)
print(len(names))


# In[7]:


#mainStat text-centre
goals = driver.find_elements_by_xpath('//td[@class="mainStat text-centre"]')
num_goals = []
for g in goals:
    num_goals.append(g.text)
print(num_goals)


# In[9]:


All_nationalities = driver.find_elements_by_xpath('//span[@class="playerCountry"]')
nationalities = []
for n in All_nationalities:
    nationalities.append(n.text)
print(len(nationalities))


# In[30]:


data = {'Rank':rank, 'Name':names, 'Number of goals':num_goals, 'Nationalities':nationalities}
data1 = pd.DataFrame(data)
data1.head()


# In[32]:


data1.to_csv("data1.csv", index=False)
driver.close()


# In[ ]:





# In[37]:


data2 = pd.read_json (r'players_stats.json')
print(len(data2))


# In[27]:


data2.to_csv ('data2.csv', index = False)
data2.head()


# In[36]:


all_data = pd.merge(data1, data2, how='left', on=['Name'])
print(len(all_data))
all_data.head()


# In[ ]:





# In[ ]:





# In[ ]:




