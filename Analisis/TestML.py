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

# Creo el grafico que me pide posta
N_div_L2 = df['N'] / (df['L'] ** 2)

plt.plot(N_div_L2, M, marker='o', linestyle='-')
plt.title('Gráfico de N/L^2 vs M')
plt.xlabel('N/L^2')
plt.ylabel('M')
plt.grid(True)
plt.show()
