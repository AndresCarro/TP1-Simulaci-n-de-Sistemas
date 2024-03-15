import pandas as pd
import matplotlib.pyplot as plt

# Lee el archivo CSV
df = pd.read_csv('MLAnalysis.csv')

# Extrae las columnas M y L
M = df['M']
L = df['L']

# Crea el gráfico
plt.plot(L, M, marker='o', linestyle='-')
plt.title('Gráfico de L vs M')
plt.xlabel('L')
plt.ylabel('M')
# Agrega la recta roja de pendiente 1.5

plt.grid(True)
plt.show()

'''
La pendiente deberia de ser 1.5 pero esta dando 1.25
Esto es raro porque cuando hago con radios 1 y 5 da 7 la pendiente
Y aca que hago con 1 y 0.25 me da 1.25 y no 1.5
Nose si será una cuestion de escala o que el criterio de corte del M no es el mejor.
'''

# Creo el grafico que me pide posta
N_div_L2 = df['N'] / (df['L'] ** 2)

plt.plot(N_div_L2, M, marker='o', linestyle='-')
plt.title('Gráfico de N/L^2 vs M')
plt.xlabel('N/L^2')
plt.ylabel('M')
plt.grid(True)
plt.show()
