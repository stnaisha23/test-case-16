import { useCallback, useState } from "react";
import ErrorBoundary from "./ErrorBoundary";

function App() {
  const [comp, setComp] = useState(null);

  const handleClick = useCallback(() => {
    setComp({"something": "is wrong"})
  }, []);

  return (
    <ErrorBoundary>
      <div style={{ height: '100vh', width: '100vw', display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center' }}>
        <button onClick={handleClick}>Do something that will cause error</button>
        {comp}
      </div>
    </ErrorBoundary>
  );
}

export default App;
